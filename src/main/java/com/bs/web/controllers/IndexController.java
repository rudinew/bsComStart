package com.bs.web.controllers;


import com.bs.web.utils.UserIPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rudoman on 14.07.2017
 */
@Controller
public class IndexController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
    /*http://stackoverflow.com/questions/16894900/spring-autowiring-service-doesnt-work-in-my-controller*/


    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
       // model.addAttribute("personlist", beePersonService.getBeePersonOrderByLastnameAsc()); //сортировка
        // model.addAttribute("personlist", beePersonService.getBeePersonAll());  //работает но без сортировки

        LOG.info("{} got index page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));  ///when debug or info
        return "index";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        //  System.out.println(user.getUsername() + "from unauthorized");
        return "unauthorized";
    }

}
