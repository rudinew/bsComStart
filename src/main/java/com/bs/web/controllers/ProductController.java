package com.bs.web.controllers;

import com.bs.backend.domain.Actions;
import com.bs.backend.domain.Product;
import com.bs.backend.domain.Users;
import com.bs.backend.service.ActionsService;
import com.bs.backend.service.ProductService;
import com.bs.backend.service.UserService;
import com.bs.web.utils.UserIPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes("product")
public class ProductController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    /*http://stackoverflow.com/questions/16894900/spring-autowiring-service-doesnt-work-in-my-controller*/
   // @Resource(name="firmsService")
    @Autowired
    private ProductService productService;
    //журнал дій
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private UserService userService;

    @RequestMapping("/products")
    public String initProducts(Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("product", productService.getProductByUsersOrderByNameAsc(users));

        LOG.info("{} got products page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "directories/products";
    }



    /**
     * удаление
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/products/{productId}/delete", method = RequestMethod.GET)
    public String deleteForm(@PathVariable("productId") Long productId, HttpServletRequest request) {
        Product product = productService.getProductByOne(productId);
        String productName = product.getName();

        this.productService.deleteProduct(productId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());

        String mes = String.format("видалено запис %s  (userIP = %s , SessionId = %s )",
                productName,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        Actions action = new Actions(users, mes, "Довідник Номенклатура");

        this.actionsService.saveAction(action);

       // LOG.info(mes);
        LOG.info("{} deleted firm - {} (userIP = {}, SessionId = {})",
                user.getUsername(),
                productName,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "redirect:/products";
    }

    @RequestMapping(value = "/products/new", method = RequestMethod.GET)
    public String initCreationForm(Model model, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());
        Product product = new Product();

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("product",product);
        model.addAttribute("group", productService.getParentProduct(users));
        //  model.addAttribute("citizen",beeTypeService.getBeeCitizenshipAll());

        LOG.info("{} got products/new page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "directories/createOrUpdateProductForm";
    }

    @RequestMapping(value = "/products/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("product") @Valid Product product,
                                      BindingResult result,
                                      SessionStatus status,
                                      HttpServletRequest request) { //(@ModelAttribute("taxOne") BeeTax beeTax, BindingResult result, SessionStatus status)

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (result.hasErrors()) {
            LOG.warn("{} had errors - {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    result.toString(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request)
            );
            return "directories/createOrUpdateProductForm";
        }

        try {
            ///
            // this.beePersonService.saveAndFlushBeePerson(beePerson);
            this.productService.saveAndFlushProduct(product, user);
            status.setComplete();
            LOG.info("{} inserted new product {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    product.getName(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
        } catch (Exception e) {
            LOG.error("{} got errors - {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    e.getMessage(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
            return e.getMessage();

        }
        return "redirect:/products";
    }



    @RequestMapping(value = "/products/{productId}/edit", method = RequestMethod.GET)
    public String viewCard(@PathVariable("productId") long productId, Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    Users users = userService.getUserByLogin(user.getUsername());
        Product product = productService.getProductByOne(productId);

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("product", product);
        LOG.info("{} got product page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "directories/createOrUpdateProductForm";
    }

    //разница между PUT POST
    // https://ru.stackoverflow.com/questions/193978/%D0%A0%D0%B0%D0%B7%D0%BD%D0%B8%D1%86%D0%B0-%D0%BC%D0%B5%D0%B6%D0%B4%D1%83-put-%D0%B8-post
    @RequestMapping(value = "/products/{productId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute("product") @Valid Product product,
                                    BindingResult result,
                                    SessionStatus status,
                                    HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());

        if (result.hasErrors()) {
            LOG.warn("{} had errors - {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    result.toString(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
            return  "directories/createOrUpdateProductForm";
        }

        try {
            this.productService.saveAndFlushProduct(product, user);

            String mes = String.format("редаговано запис %s  (userIP = %s , SessionId = %s )",
                    product.getName(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
            Actions action = new Actions(users, mes, "Довідник Номенклатура");

            this.actionsService.saveAction(action);

            status.setComplete();
            LOG.info("{} updated product {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    product.getName(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));



        } catch (Exception e) {
            LOG.error("{} got errors - {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    e.getMessage(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
            return e.getMessage();

        }
        return "redirect:/products";
    }



}
