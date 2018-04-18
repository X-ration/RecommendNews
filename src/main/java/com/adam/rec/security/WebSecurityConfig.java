package com.adam.rec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author adam
 * 创建于 2018-04-17 17:13.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().and()
//                .authorizeRequests()
//                .antMatchers("/css/**","/images/**","/js/**","/webjars/**")
//                .permitAll()
//                .antMatchers("/","/viewNews","/error","/login","/signup","/viewNews/{newsId}")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/secureLogin")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
        http

                .formLogin()
                .loginPage("/login")   //自定义登录页
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/webjars/**","/css/**","/js/**","/images/**","/login","/signup","/","/viewNews","/viewNews/{newsId}").permitAll()
                .anyRequest().authenticated();
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Adam").password("Adam").roles("USER");
    }

//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails userDetails =
//                User
//                .withUsername("Adam")
//                .password("Adam")
//                .roles("USER")
//                .build();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(userDetails);
//        return manager;
//    }

}
