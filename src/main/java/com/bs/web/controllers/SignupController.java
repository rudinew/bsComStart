package com.bs.web.controllers;


import com.bs.backend.domain.Users;
import com.bs.backend.domain.UserRole;
import com.bs.backend.service.UserService;
import com.bs.web.utils.UserIPUtils;
import com.bs.web.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@SessionAttributes("Users")
public class SignupController {
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private UserService userService;

    public static final String SIGNUP_URL_MAPPING = "/signup";

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signUpGet(Model model, HttpServletRequest request) {
        Users beeUsers = new Users();
        model.addAttribute("beeUsers",beeUsers);
        //for test
        LOG.info("return signup page (userIP = {}, SessionId = {})",
                UserIPUtils.getClientIp(request),
                WebUtils.getSessionId(request));

        return "registration/signup";
    }

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.POST)
    public String signUpPost(@ModelAttribute("beeUsers") @Valid Users beeUsers,
                             BindingResult result,
                             SessionStatus status,
                             HttpServletRequest request) {

        new UserValidator().validate(beeUsers, result);
        if (result.hasErrors()) {
            LOG.info("Try to sign up. Have an error - {} (userIP = {}, SessionId = {})",
                    result.toString(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));  ///when debug or info
            return "registration/signup";
        } else {
            beeUsers.setIs_active(true);
            beeUsers.setRole(UserRole.ROLE_CLIENT);

            this.userService.addUser(beeUsers);
            status.setComplete();
            LOG.info("{} - sign up success! (userIP = {}, SessionId = {})",
                    beeUsers.getLogin(),
                    UserIPUtils.getClientIp(request),
                    WebUtils.getSessionId(request));  ///when debug or info
            return "redirect:/";
        }
    }






}
