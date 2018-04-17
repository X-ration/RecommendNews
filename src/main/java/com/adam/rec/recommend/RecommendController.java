package com.adam.rec.recommend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author adam
 * 创建于 2018-04-17 11:21.
 */
@Controller
public class RecommendController {

    @RequestMapping("/recommend")
    public String recommend() {
        return "recommend/recommend";
    }

}
