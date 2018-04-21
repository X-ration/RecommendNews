package com.adam.rec.user;

import com.adam.rec.news.NewsCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-12 23:03.
 */
@Controller
public class UserController {

    private UserService userServiceJdbc;
    private CityRepository cityRepository;
    private NewsCategories newsCategories;

    @Autowired
    public UserController(UserServiceJdbc userServiceJdbc, CityRepository cityRepository,NewsCategories newsCategories) {
        this.userServiceJdbc = userServiceJdbc;
        this.cityRepository = cityRepository;
        this.newsCategories = newsCategories;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginGet() {
        return "login/login";
    }

    @ModelAttribute
    public UserForm getUserForm() {
        return new UserForm();
    }

    @ModelAttribute
    public LoginForm getLoginForm() {
        return new LoginForm();
    }

    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup/signup");
        modelAndView.addObject("cities",cityRepository.getCities());
        modelAndView.addObject("categories",newsCategories.getCategories());
        return modelAndView;
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signup(UserForm userForm, RedirectAttributes redirectAttributes) {
        System.out.println(userForm);
        String messageUserForm = UserUtil.analyzeUserFormValidity(userForm);
        if(messageUserForm == null) {
            User user = UserUtil.convertToUser(userForm);
            System.out.println(user);
            Boolean result = userServiceJdbc.writeUser(user);
            if (result) return "redirect:/login";
            else {
                redirectAttributes.addFlashAttribute("error", "注册失败：写入到数据库时发生异常");
                return "redirect:/signup";
            }
        } else {
            redirectAttributes.addFlashAttribute("error","无效表单："+messageUserForm);
            return "redirect:/signup";
        }

    }

}
