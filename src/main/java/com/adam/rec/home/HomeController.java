package com.adam.rec.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 根控制器。
 */

@Controller
public class HomeController {

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

}
