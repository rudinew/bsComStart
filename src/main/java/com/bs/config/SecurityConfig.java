package com.bs.config;


import com.bs.web.controllers.SignupController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /*http://www.jeejava.com/preauthorize-annotation-haspermission-example-in-spring-security/*/
 /*   @Autowired
    private BasePermissionEvaluator permissionEvaluator;*/
    //https://stackoverflow.com/questions/42562893/could-not-autowire-no-beans-of-userdetailservice-type-found
    //userDetailsServiceBean()
    //Override this method to expose a UserDetailsService created from configure(AuthenticationManagerBuilder) as a bean.
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

     @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getBCryptPasswordEncoder()); //passwordencoder()

    }

    /*http://sha1.gromweb.com/?hash=5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8
The SHA-1 hash:
5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8
was succesfully reversed into the string:
password
*/

    /** Public URLs. */
    private static final String[] PUBLIC_MATCHERS = {
            "/static/**",
          //  ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING,
      //      ForgotMyPasswordController.CHANGE_PASSWORD_PATH,
            SignupController.SIGNUP_URL_MAPPING
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //  .csrf().disable()
                .authorizeRequests()
                //  .antMatchers("/templates/**").authenticated()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/login.html") //"/unauthorized"
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login.html?error")
                .usernameParameter("j_login")
                .passwordParameter("j_password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout")
                .and()
                .rememberMe()
                .and()
                .csrf()
                .and()
                .requiresChannel()
                .antMatchers("/templates/personal/**").requiresSecure();  //require HTTPS
               /* .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1209600);*/
                 ///
        //  .invalidateHttpSession(true);


    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
