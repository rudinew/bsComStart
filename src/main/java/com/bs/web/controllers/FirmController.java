package com.bs.web.controllers;

import com.bs.backend.domain.Actions;
import com.bs.backend.domain.Firms;
import com.bs.backend.domain.Users;
import com.bs.backend.service.ActionsService;
import com.bs.backend.service.FirmsService;
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
@SessionAttributes("firms")  ///post сработал иначе пусто
public class FirmController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(FirmController.class);

    /*http://stackoverflow.com/questions/16894900/spring-autowiring-service-doesnt-work-in-my-controller*/
   // @Resource(name="firmsService")
    @Autowired
    private FirmsService firmsService;
    //журнал дій
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private UserService userService;

    @RequestMapping("/firms")
    public String initFirms(Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("firms", firmsService.getFirmsByUsersOrderByNameAsc(users));

        LOG.info("{} got firms page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "directories/firms";
    }



    /**
     * удаление всей инфы по фирме
     *
     * @param firmId
     * @return
     */
    @RequestMapping(value = "/firms/{firmId}/delete", method = RequestMethod.GET)
    public String deleteForm(@PathVariable("firmId") Long firmId, HttpServletRequest request) {
        Firms firm = firmsService.getFirmByOne(firmId);
        String beeFirmName = firm.getName();

        this.firmsService.deleteFirm(firmId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());

        String mes = String.format("видалено запис %s  (userIP = %s , SessionId = %s )",
                beeFirmName,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        Actions action = new Actions(users, mes, "Довідник Фірми");

        this.actionsService.saveAction(action);

       // LOG.info(mes);
        LOG.info("{} deleted firm - {} (userIP = {}, SessionId = {})",
                user.getUsername(),
                beeFirmName,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "redirect:/firms";
    }

    @RequestMapping(value = "/firms/new", method = RequestMethod.GET)
    public String initCreationForm(Model model, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Firms firms = new Firms();

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("firms",firms);
        //  model.addAttribute("citizen",beeTypeService.getBeeCitizenshipAll());

        LOG.info("{} got firms/new page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "directories/createOrUpdateFirmForm";
    }

    @RequestMapping(value = "/firms/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("firms") @Valid Firms firms,
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
            return "directories/createOrUpdateFirmForm";
        }

        try {
            ///
            // this.beePersonService.saveAndFlushBeePerson(beePerson);
            this.firmsService.saveAndFlushFirms(firms, user);
            status.setComplete();
            LOG.info("{} inserted new firm {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    firms.getName(),
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
        return "redirect:/firms";
    }



    @RequestMapping(value = "/firms/{firmId}/edit", method = RequestMethod.GET)
    public String viewCard(@PathVariable("firmId") long firmId, Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //    Users users = userService.getUserByLogin(user.getUsername());
        Firms firm = firmsService.getFirmByOne(firmId);

        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("firms", firm);
        LOG.info("{} got firm page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "directories/createOrUpdateFirmForm";
    }

    //разница между PUT POST
    // https://ru.stackoverflow.com/questions/193978/%D0%A0%D0%B0%D0%B7%D0%BD%D0%B8%D1%86%D0%B0-%D0%BC%D0%B5%D0%B6%D0%B4%D1%83-put-%D0%B8-post
    @RequestMapping(value = "/firms/{firmId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute("firms") @Valid Firms firms,
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
            return  "directories/createOrUpdateFirmForm";
        }

        try {
            this.firmsService.saveAndFlushFirms(firms, user);

            String mes = String.format("редаговано запис %s  (userIP = %s , SessionId = %s )",
                    firms.getName(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));
            Actions action = new Actions(users, mes, "Довідник Фірми");

            this.actionsService.saveAction(action);

            status.setComplete();
            LOG.info("{} updated firm {} (userIP = {}, SessionId = {})",
                    user.getUsername(),
                    firms.getName(),
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
        return "redirect:/firms";
    }



}
