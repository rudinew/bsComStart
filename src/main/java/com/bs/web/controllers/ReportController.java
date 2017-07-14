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


@Controller
public class ReportController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    @RequestMapping("/reports")
    public String index(Model model, HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());

        LOG.info("{} got actions page (userIP = {}, SessionId = {})",
                user.getUsername(),
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));
        return "reports/report1";
    }



}
