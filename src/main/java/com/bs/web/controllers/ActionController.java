package com.bs.web.controllers;

import com.bs.backend.domain.Users;
import com.bs.backend.service.ActionsService;
import com.bs.backend.service.UserService;
import com.bs.web.utils.UserIPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ActionController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ActionController.class);

    //журнал дій
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private UserService userService;

    @RequestMapping("/actions")
    public String index(Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.getUserByLogin(user.getUsername());
        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("actions", actionsService.getActionsByUsersOrderByDtFromAsc(users));

        LOG.info("{} got actions page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "settings/actions";
    }



}
