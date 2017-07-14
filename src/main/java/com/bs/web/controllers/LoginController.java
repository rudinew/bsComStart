package com.bs.web.controllers;


import com.bs.web.utils.UserIPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping
    public String loginPage(HttpServletRequest request) {
     //for test
        LOG.info("return login page (userIP = {}, SessionId = {})",
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "user/login";
    }
}