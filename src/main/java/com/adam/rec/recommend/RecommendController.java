package com.adam.rec.recommend;

import com.adam.rec.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class RecommendController {

    private UserSession userSession;

    @Autowired
    public RecommendController(UserSession userSession) {
        this.userSession = userSession;
    }

    @RequestMapping("/recommend")
    public String recommend(RedirectAttributes redirectAttributes) {
        if(!userSession.isConnected()){
            redirectAttributes.addFlashAttribute("error","请先登录！");
            return "redirect:/login";
        }
        return "recommend/recommend";
    }

}
