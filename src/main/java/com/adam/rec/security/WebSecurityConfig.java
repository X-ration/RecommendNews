package com.adam.rec.security;

/**
 * @author adam
 * 创建于 2018-04-17 17:13.
 */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/","/viewNews","/error","/signup","/viewNews/{newsId}").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
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
//}
