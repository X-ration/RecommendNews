package com.adam.rec.user_news;

import com.adam.rec.news.NewsService;
import com.adam.rec.news.NewsServiceJdbc;
import com.adam.rec.user.UserService;
import com.adam.rec.user.UserServiceJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author adam
 * 创建于 2018-04-18 13:25.
 */
@Controller
public class EvaluationController {

    private UserService userServiceJdbc;
    private UserNewsService userNewsServiceJdbc;
    private NewsService newsServiceJdbc;

    @Autowired
    public EvaluationController(UserServiceJdbc userServiceJdbc, UserNewsService userNewsServiceJdbc, NewsService newsServiceJdbc) {
        this.userServiceJdbc = userServiceJdbc;
        this.userNewsServiceJdbc = userNewsServiceJdbc;
        this.newsServiceJdbc = newsServiceJdbc;
    }

    @RequestMapping(value = "/viewNews/{newsId}/evaluation", params = "submitEvaluation", method = RequestMethod.POST)
    public String newEvaluation(EvaluationForm evaluationForm, @PathVariable int newsId, RedirectAttributes redirectAttributes) {
        if(!EvaluationUtil.isValid(evaluationForm)){
            redirectAttributes.addFlashAttribute("error",EvaluationUtil.whyInvalid(evaluationForm));
        } else {
            redirectAttributes.addFlashAttribute("error","感谢您的评价！");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            int userId = userServiceJdbc.getUserIdByName(userDetails.getUsername());
            Evaluation evaluation = EvaluationUtil.buildEvaluation(evaluationForm,userId,newsId);

            newsServiceJdbc.receiveEvaluation(evaluation,
                    userNewsServiceJdbc.getPrevScore(evaluation.getUserId(),evaluation.getNewsId()),
                    userNewsServiceJdbc.getPrevEvaluation(evaluation.getUserId(),evaluation.getNewsId()),
                    !userNewsServiceJdbc.writeOrUpdate(evaluation));
            userNewsServiceJdbc.saveEvaluation(evaluation);

            System.out.println(evaluation);
        }
        return "redirect:/viewNews/"+newsId;
    }

}
