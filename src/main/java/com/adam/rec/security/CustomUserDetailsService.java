package com.adam.rec.security;

import com.adam.rec.jdbc.JdbcUtil;
import com.adam.rec.user.User;
import com.adam.rec.user.UserService;
import com.adam.rec.user.UserServiceJdbc;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-18 10:05.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UserService userServiceJdbc;

    @Autowired
    public CustomUserDetailsService(UserServiceJdbc userServiceJdbc) {
        this.userServiceJdbc = userServiceJdbc;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userServiceJdbc.getUserByName(username);
        org.springframework.security.core.userdetails.User user1 = null;
        if(user==null) {
            throw new UsernameNotFoundException("用户不存在！");
        } else {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
            user1 = new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
        }
        return user1;
    }
}
