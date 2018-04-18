package com.adam.rec.profile;

import com.adam.rec.user.UserService;
import com.adam.rec.user.UserServiceJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class ProfileController {

    private UserService userServiceJdbc;

    @Autowired
    public ProfileController(UserServiceJdbc userServiceJdbc) {
        this.userServiceJdbc = userServiceJdbc;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String city = userServiceJdbc.getUserCityByName(userDetails.getUsername());
        ModelAndView modelAndView = new ModelAndView("profile/profile");
        modelAndView.addObject("city",city);
        return modelAndView;
    }

}
