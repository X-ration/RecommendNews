package com.adam.rec.profile;

import com.adam.rec.news.NewsCategories;
import com.adam.rec.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class ProfileController {

    private UserService userServiceJdbc;
    private CityRepository cityRepository;
    private NewsCategories newsCategories;

    @Autowired
    public ProfileController(UserServiceJdbc userServiceJdbc,CityRepository cityRepository,NewsCategories newsCategories) {
        this.userServiceJdbc = userServiceJdbc;
        this.cityRepository = cityRepository;
        this.newsCategories = newsCategories;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
//        String city = userServiceJdbc.getUserCityByName(userDetails.getUsername());
        User user = userServiceJdbc.getUserByName(userDetails.getUsername());
        ModelAndView modelAndView = new ModelAndView("profile/profile");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/profile/modify",method = RequestMethod.GET)
    public ModelAndView modifyProfile() {

        ModelAndView modelAndView = new ModelAndView("profile/modifyProfile");
        modelAndView.addObject("cities",cityRepository.getCities());
        modelAndView.addObject("categories",newsCategories.getCategoriesList());
        return modelAndView;
    }

    @ModelAttribute
    public UserForm generateUserForm() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userServiceJdbc.getUserByName(userDetails.getUsername());
        return UserUtil.convertToUserForm(user);
    }

    @RequestMapping(value = "/profile/modify",method = RequestMethod.POST)
    public String modifyProfilePost(UserForm userForm, RedirectAttributes redirectAttributes) {
        User user = UserUtil.convertToUser(userForm);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        userForm.setName(userDetails.getUsername());
        System.out.println(userForm);
        String messageUserForm = UserUtil.analyzeUserFormValidity(userForm);
        if(messageUserForm == null) {

            User userPrev = userServiceJdbc.getUserByName(userDetails.getUsername());
            user.setUserId(userPrev.getUserId());

            System.out.println(user);
            Boolean result = userServiceJdbc.updateUser(user);
            if (result) return "redirect:/profile";
            else {
                redirectAttributes.addFlashAttribute("error", "修改资料失败：写入到数据库时发生异常");
                return "redirect:/profile/modify";
            }
        } else {
            redirectAttributes.addFlashAttribute("error","无效表单："+messageUserForm);
            return "redirect:/profile/modify";
        }
    }

}
