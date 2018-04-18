package com.adam.rec.user;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserSession userSession;
    private CityRepository cityRepository;

    @Autowired
    public UserController(UserServiceJdbc userServiceJdbc, UserSession userSession, CityRepository cityRepository) {
        this.userServiceJdbc = userServiceJdbc;
        this.userSession = userSession;
        this.cityRepository = cityRepository;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginGet() {
        return "login/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginPost(LoginForm loginForm, RedirectAttributes redirectAttributes) {
        Boolean result = userServiceJdbc.checkUser(loginForm.getUsername(),loginForm.getPassword());
        if(result){
            System.out.println("用户"+loginForm.getUsername()+"登陆成功");
            userSession.login(userServiceJdbc.getUserByName(loginForm.getUsername()));
            System.out.println(userSession.getUserSession());
            return "redirect:/";
        } else {
            System.out.println("用户"+loginForm.getUsername()+"登录失败");
            return "redirect:/login";
        }
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
        return modelAndView;
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signup(UserForm userForm, RedirectAttributes redirectAttributes) {
        System.out.println(userForm);
        String messageUserForm = UserUtil.analyzeUserFormValidity(userForm);
        if(messageUserForm == null) {
            User user = UserUtil.convertToUser(userForm);
            System.out.println(user);
            Boolean result = userServiceJdbc.writeUser(user,userForm.getPassword());
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

    @RequestMapping("/secureLogin")
    public String secureLogin() {
        return "login/secureLogin";
    }


}
