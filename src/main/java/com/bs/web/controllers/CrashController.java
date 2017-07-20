package com.bs.web.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Rudoman on 14.06.2017.
 */
//РАЗОБРАТЬ
    //https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc

//http://www.baeldung.com/exception-handling-for-rest-with-spring
@ControllerAdvice
public class CrashController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "CrashController - This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}


/*
* from udemy
*
* @ExceptionHandler({StripeException.class, S3Exception.class})
* public ModelAndView signupException(HttpServletRequest request, Exception exception){
* LOG.error("Request {} raised exception {}", request.getRequestURL(), exception);
* ModelAndView mav = new ModelAndView();
* mav.addObject("exception", exception);
* mav.addObject("timestamp", LocalDate.now(Clock.systemUTC()));
* mav.setViewName(GENERIC_ERROR_VIEW_NAME);
* return mav;
* }*/

/*{
    // The application logger
    private static final Logger LOG = LoggerFactory.getLogger(CrashController.class);

    @RequestMapping(value = "/oups", method = RequestMethod.GET)
    public String triggerException() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOG.info(user.getUsername() + " got crash page");
        throw new RuntimeException("Expected: controller used to showcase what " +
                "happens when an exception is thrown");
    }
}*/
