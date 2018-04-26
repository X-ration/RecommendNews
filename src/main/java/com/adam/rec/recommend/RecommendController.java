package com.adam.rec.recommend;

import com.adam.rec.user.User;
import com.adam.rec.user.UserService;
import com.adam.rec.user.UserServiceJdbc;
import com.adam.rec.user.UserUtil;
import org.codehaus.janino.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class RecommendController {

    private RecommendService recommendServiceJdbc;
    private UserService userServiceJdbc;

    @Autowired
    public RecommendController(RecommendServiceJdbc recommendServiceJdbc, UserServiceJdbc userServiceJdbc) {
        this.recommendServiceJdbc = recommendServiceJdbc;
        this.userServiceJdbc = userServiceJdbc;
    }

    @RequestMapping("/recommend")
    public ModelAndView recommend(@RequestParam(defaultValue = "1") int page) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userServiceJdbc.getUserByName(userDetails.getUsername());

        ModelAndView modelAndView = new ModelAndView("recommend/recommend");
        modelAndView.addObject(recommendServiceJdbc.getNewsListFilteredPage(user.getInterests(),page));
        return modelAndView;
    }

}
