package com.adam.rec.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻相关控制器。
 */

@Controller
public class NewsController {

    private NewsServiceSpark newsServiceSpark;
    private NewsServiceJdbc newsServiceJdbc;

    @Autowired
    public NewsController(NewsServiceSpark newsServiceSpark, NewsServiceJdbc newsServiceJdbc) {
        this.newsServiceSpark = newsServiceSpark;
        this.newsServiceJdbc = newsServiceJdbc;
        try {
            //this.newsServiceJdbc.writeNewsList(newsServiceSpark.getNewsListByIdRange(1,1000));  //将使用Spark SQL查询得到的ID小于1000的写入Oracle数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看所有新闻。
     * @return 包含10条新闻数据的页面。
     */
    @RequestMapping(value = "/viewNews", method = RequestMethod.GET)
    public ModelAndView viewNews(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsList");
        modelAndView.addObject("newsList",newsServiceJdbc.getNewsListWindow());
        return modelAndView;
    }

    @RequestMapping(value = "/viewNews/{newsId}", method = RequestMethod.GET)
    public ModelAndView viewNewsDetail(@PathVariable int newsId) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsDetail");
        modelAndView.addObject("news",newsServiceJdbc.getNewsById(newsId));
        return modelAndView;
    }

}
