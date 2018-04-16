package com.adam.rec.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-12 23:03.
 */
@Controller
public class UserController {

    private UserService userServiceJdbc;

    @Autowired
    public UserController(UserServiceJdbc userServiceJdbc) {
        this.userServiceJdbc = userServiceJdbc;
    }

    @RequestMapping("/login")
    public String login() {
        return "login/login";
    }

    @ModelAttribute
    public UserForm getUserForm() {
        return new UserForm();
    }

    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String signup() {
        return "signup/signup";
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signup(UserForm userForm, RedirectAttributes redirectAttributes) {
        User user = UserUtil.convertToUser(userForm);
        System.out.println(user);
        Boolean result = userServiceJdbc.writeUser(user);
        if(result) return "redirect:/login";
        else {
            redirectAttributes.addFlashAttribute("error","注册失败，请重试！可能的原因：用户名重复;解析异常");
            return "redirect:/signup";
        }
    }


}
