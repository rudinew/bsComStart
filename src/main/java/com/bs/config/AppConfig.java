package com.bs.config;


import com.bs.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;

@Configuration
//@PropertySource("classpath:config.properties")
@PropertySource("classpath:application.properties")  ///comment for mycloud
@EnableTransactionManagement
@EnableWebMvc  //without this mistake - not define MultipartResolver
// @ComponentScan("com.bee")
//http://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html
//Spring Boot tries to guess the location of your @Repository definitions, based on the @EnableAutoConfiguration it finds.
// To get more control, use the @EnableJpaRepositories annotation (from Spring Data JPA).
/*@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = { "com.bee.domain" })*/
@EnableJpaRepositories("com.bs.backend.repositories")
/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
 * If you want to keep Spring Boot MVC features, and you just want to add additional MVC configuration (interceptors, formatters, view controllers etc.)
 * you can add your own @Configuration class of type WebMvcConfigurerAdapter, but without @EnableWebMvc.
 */
public class AppConfig extends WebMvcConfigurerAdapter /*implements ApplicationContextAware*/ {
    @Value("${jdbc.driver}")
    private String jdbcDriver; // ="com.mysql.jdbc.Driver";

    @Value("${host}")
    private String jdbcURL; //="jdbc:mysql://mysql43796-beedocs.mycloud.by/dbdiplom";

    @Value("${jdbc.username}")
    private String jdbcUsername; // = "root";

    @Value("${jdbc.password}")
    private String jdbcPassword; // = "L6Cse7H55z";

    @Value("${hibernate.dialect}")
    private String sqlDialect;
    //from thymeleafexamples.springsecurity
    // private ApplicationContext applicationContext;

   /* public AppConfig() {
        super();
    }


    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }*/


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory
            (DataSource dataSource, JpaVendorAdapter jpaVendeorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendeorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.setPackagesToScan("com.bs");
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(false); //15.06.2017 не показывать sql
        adapter.setDatabasePlatform(sqlDialect);

        return adapter;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(jdbcDriver);
        ds.setUrl(jdbcURL);
        ds.setUsername(jdbcUsername);
        ds.setPassword(jdbcPassword);

        return ds;
    }

     /**
     * THYMELEAF: Template Resolver for webapp pages.
      *
      * а есть еще представления JSP, Velocity, FreeMarker
      *
      * from Spring's documentation "As of Spring Framework 4.3,
      * Velocity support has been deprecated due to six years without active maintenance of the Apache Velocity project.
      * We recommend Spring’s FreeMarker support instead, or Thymeleaf which comes with Spring support itself."
     */

     @Bean
     public TemplateResolver templateResolver() {
         TemplateResolver templateResolver = new ServletContextTemplateResolver();
         templateResolver.setPrefix("/WEB-INF/templates/");
         templateResolver.setSuffix(".html");
         templateResolver.setTemplateMode("HTML5");
         templateResolver.setOrder(1);
         return templateResolver;
     }

    @Bean
    public TemplateResolver templateResolverLayouts() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/layouts/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setOrder(1);
        return templateResolver;
    }

    @Bean
    public TemplateResolver templateResolverPersonal() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/personal/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setOrder(1);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //  templateEngine.setEnableSpringELCompiler(true); // Compiled SpringEL should speed up executions
        //  templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(templateResolverLayouts());
        templateEngine.addTemplateResolver(templateResolverPersonal());
          //    templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        // viewResolver.setOrder(2);  //http://nixmash.com/java/thymeleaf-configuration-with-spring-boot/
        return viewResolver;
    }


    /**
     * Dispatcher configuration for serving static resources
     *
     * http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
     * 27.1.5 Static Content
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
      //  registry.addResourceHandler("/static/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/static/images/");


    }


    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }



    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        return properties;
    }

    /**
     * i18n
     * http://stackoverflow.com/questions/36531131/i18n-in-spring-boot-thymeleaf
      */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.forLanguageTag("uk"));  ///  "en-US" пробую задать язык по умолчанию - вышло)
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");

        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }


}

