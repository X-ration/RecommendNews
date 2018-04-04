package com.adam.rec.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻相关控制器。
 */

@Controller
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * 查看所有新闻。
     * @return 包含10条新闻数据的页面。
     */
    @RequestMapping("/viewNews")
    public ModelAndView viewNews(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("viewNews");
        modelAndView.addObject("newsList",newsService.getNewsListTenDynamic());
        return modelAndView;
    }

}
