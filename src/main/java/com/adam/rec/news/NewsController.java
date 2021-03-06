package com.adam.rec.news;

import com.adam.rec.page.PagePaginationBuilder;
import com.adam.rec.user_news.EvaluationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻相关控制器。
 */

@Controller
public class NewsController {

    private NewsService newsServiceSpark;
    private NewsService newsServiceJdbc;
    private NewsCategories newsCategories;
    private int maxPage;

    @Autowired
    public NewsController(NewsService newsServiceSpark, NewsService newsServiceJdbc, NewsCategories newsCategories, @Qualifier("maxPage") int maxPage) {
        this.newsServiceSpark = newsServiceSpark;
        this.newsServiceJdbc = newsServiceJdbc;
        this.newsCategories = newsCategories;
        this.maxPage = maxPage;
        try {
//            this.newsServiceJdbc.writeNewsList(newsServiceSpark.getNewsListByCategoriesAndAmount(newsCategories.getCategoriesList(),100));  //将使用Spark SQL查询得到的前1000条的写入Oracle数据库
//            this.                  .writeNewsList(newsServiceSpark.getNewsListByIdRange(1,1001));
            System.out.println("写入到数据库完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看所有新闻。
     * @return 包含10条新闻数据的页面。
     */
    @RequestMapping(value = "/viewNews", method = RequestMethod.GET)
    public ModelAndView viewNews(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsList");
        modelAndView.addObject("newsList",newsServiceJdbc.getNewsListPage(page));
        modelAndView.addObject("curPage",page);
        modelAndView.addObject("pagination",new PagePaginationBuilder(page,maxPage).build());
        modelAndView.addObject("maxPage",maxPage);
        return modelAndView;
    }

    @RequestMapping(value = "/viewNews/{newsId}", method = RequestMethod.GET)
    public ModelAndView viewNewsDetail(@PathVariable int newsId) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsDetail");
        modelAndView.addObject("news",newsServiceJdbc.getNewsById(newsId));
        return modelAndView;
    }

    @ModelAttribute
    public EvaluationForm getEvaluationForm() {
        return new EvaluationForm();
    }



}
