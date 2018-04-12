package com.adam.rec.news;

import com.adam.rec.news.page.PagePaginationBuilder;
import com.adam.rec.user_news.Evaluation;
import com.adam.rec.user_news.EvaluationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView viewNews(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsList");
        modelAndView.addObject("newsList",newsServiceJdbc.getNewsListPage(page));
        modelAndView.addObject("curPage",page);
        modelAndView.addObject("pagination",new PagePaginationBuilder(page).build());
        modelAndView.addObject("maxPage",PagePaginationBuilder.maxPage);
        return modelAndView;
    }

    @RequestMapping(value = "/viewNews/{newsId}", method = RequestMethod.GET)
    public ModelAndView viewNewsDetail(@PathVariable int newsId) {
        ModelAndView modelAndView = new ModelAndView("news/viewNewsDetail");
        modelAndView.addObject("news",newsServiceJdbc.getNewsById(newsId));
        return modelAndView;
    }

    @ModelAttribute
    public Evaluation getEvaluation() {
        return new Evaluation();
    }

    @RequestMapping(value = "/viewNews/{newsId}/evaluation", params = "submitEvaluation", method = RequestMethod.POST)
    public String newEvaluation(Evaluation evaluation, @PathVariable int newsId, RedirectAttributes redirectAttributes) {
        System.out.println("" + newsId + evaluation);
        if(!EvaluationUtil.isValid(evaluation)){
            redirectAttributes.addFlashAttribute("error",EvaluationUtil.whyInvalid(evaluation));
        } else {
            redirectAttributes.addFlashAttribute("error","感谢您的评价！");
        }
        return "redirect:/viewNews/"+newsId;
    }

}
