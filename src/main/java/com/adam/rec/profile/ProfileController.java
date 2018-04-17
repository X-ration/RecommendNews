package com.adam.rec.profile;

import com.adam.rec.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class ProfileController {

    private UserSession userSession;

    @Autowired
    public ProfileController(UserSession userSession) {
        this.userSession = userSession;
    }

    @RequestMapping("/profile")
    public String profile() {
        return "profile/profile";
    }

}
