package com.lll.online.exam.controller.admin;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.service.ExamPaperAnswerService;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageRequestVM;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台答卷Controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/4
 */
@RestController("AdminExamPaperAnswerController")
@RequestMapping("api/admin/examPaperAnswer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;

    /*
    * @Description: 答卷分页查询
    * @Param: ExamPaperAnswerPageRequestVM
    * @return: Result<PageResult<ExamPaperAnswerPageResponseVM>>
    * @Date: 2021/4/4
    */
    @PostMapping("page")
    public Result<PageResult<ExamPaperAnswerPageResponseVM>> pageList(@RequestBody ExamPaperAnswerPageRequestVM model){

        PageResult<ExamPaperAnswerPageResponseVM> pageResult = examPaperAnswerService.adminPage(model);

        return Result.ok(pageResult);
    }

}
