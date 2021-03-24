package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@RestController("AdminQuestionController")
@RequestMapping("/api/admin/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /*
    * @Description: Question的分页查询
    * @Param: QuestionPageRequestVM
    * @return: Result<PageResult<QuestionResponseVM>>
    * @Date: 2021/3/24
    */
    public Result<PageResult<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM model){
        IPage<Question> list = questionService.pageList(model);
        // TODO： 待续
        return Result.ok();
    }
}
