package com.adam.rec.user_news;

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
    @Autowired
    public EvaluationController(UserServiceJdbc userServiceJdbc,UserNewsService userNewsServiceJdbc) {
        this.userServiceJdbc = userServiceJdbc;
        this.userNewsServiceJdbc = userNewsServiceJdbc;
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
            userNewsServiceJdbc.writeEvaluation(evaluation);
            System.out.println(evaluation);
        }
        return "redirect:/viewNews/"+newsId;
    }

}
