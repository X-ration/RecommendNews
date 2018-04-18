package com.adam.rec.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String profile() {
        return "profile/profile";
    }

}
