package com.bs;


import com.bs.config.AppConfig;

import com.bs.config.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer /*extends AbstractDispatcherServletInitializer*/ implements WebApplicationInitializer {

   /* private String TMP_FOLDER = "/tmp";
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;*/

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.register(SecurityConfig.class);

        /*12.06.2017
        * when try to deploy on mycloud
         *  Exception sending context initialized event to listener instance of class org.springframework.web.context.ContextLoaderListener
          java.lang.IllegalStateException: Cannot initialize context because there is already a root application context present - check whether you have multiple ContextLoader* definitions in your web.xml!*/

      //  servletContext.addListener(new ContextLoaderListener(ctx));

        ctx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");


        servlet.setLoadOnStartup(1);
        /* 24.07.2017
        http://www.baeldung.com/spring-file-upload

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);

        servlet.setMultipartConfig(multipartConfigElement);  */

    }



  /*  public WebInitializer() {
        super();
    }



    protected WebApplicationContext createServletApplicationContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        return context;
    }

    protected WebApplicationContext createRootApplicationContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SecurityConfig.class);
        return context;
    }

    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {

        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
       //encodingFilter.setEncoding(AppConfig.CHARACTER_ENCODING);
        encodingFilter.setEncoding("UTF-8");

        encodingFilter.setForceEncoding(true);

        final DelegatingFilterProxy springSecurityFilter = new DelegatingFilterProxy("springSecurityFilterChain");

        return new Filter[] { encodingFilter, springSecurityFilter };

    }
*/

    /*@Override
    protected Class<?>[] getRootConfigClasses() {
        return  new Class<?>[] { AppConfig.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {  null };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }*/

    /*@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");

        return new Filter[] { characterEncodingFilter, new DelegatingFilterProxy("springSecurityFilterChain"),
                new OpenEntityManagerInViewFilter() };
    }*/
}