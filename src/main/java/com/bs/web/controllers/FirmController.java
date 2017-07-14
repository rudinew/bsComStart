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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@SessionAttributes("firms")  ///post сработал иначе пусто
public class FirmController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(FirmController.class);

    /*http://stackoverflow.com/questions/16894900/spring-autowiring-service-doesnt-work-in-my-controller*/
    @Resource(name="firmsService")
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

        LOG.info("{} got actions page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "directories/firms";
    }

    /**
     * вставка фирмы
     *
     * @param name
     * @return
     */
   @RequestMapping(value = "/firms/new", method = RequestMethod.POST)
    public String insertForm(String name, HttpServletRequest request) {
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Users users = userService.getUserByLogin(user.getUsername());
       Firms firm = new Firms(name, users);
       this.firmsService.saveAndFlushFirms(firm);

       String mes = String.format("%s inserted firm - %s  (userIP = %s , SessionId = %s )",
               user.getUsername(),
               firm,
               UserIPUtils.getClientIp(request),
               WebUtils.getSessionId(request));
       Actions action = new Actions(users, mes, "Довідник Фірми");

       this.actionsService.saveAction(action);

       LOG.info(mes);


       return "redirect:/cards";

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
        String mes = String.format("%s deleted firm - %s  (userIP = %s , SessionId = %s )",
                user.getUsername(),
                firm,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        Actions action = new Actions(users, mes, "Довідник Фірми");

        this.actionsService.saveAction(action);

        LOG.info(mes);
       /* LOG.info("{} deleted firm - {} (userIP = {}, SessionId = {})",
                user.getUsername(),
                firm,
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));  */

        return "redirect:/cards";
    }



}
