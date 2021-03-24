package com.lll.online.exam.controller.admin;

import com.lll.online.exam.base.Result;
import com.lll.online.exam.service.*;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.admin.diashboard.IndexVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页控制器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashBoardController {
    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserEventLogService userEventLogService;

    /*
    * @Description: 管理系统主页信息获取
    * @Param: []
    * @return: Result<IndexVM>
    * @Date: 2021/3/22
    */
    @PostMapping("index")
    public Result<IndexVM> dashboard(){
        IndexVM indexVM = new IndexVM();
        // TODO：计总数
        indexVM.setExamPaperCount(examPaperService.getAllCount());
        indexVM.setDoExamPaperCount(examPaperAnswerService.getAllCount());
        indexVM.setDoQuestionCount(examPaperQuestionCustomerAnswerService.getAllCount());
        indexVM.setQuestionCount(questionService.getAllCount());

        // TODO：计算用户日活，日答题量
        indexVM.setMothDayText(DateTimeUtil.currentMonthDay());
        indexVM.setMothDayUserActionValue(userEventLogService.getMonthCount());
        indexVM.setMothDayDoExamQuestionValue(examPaperQuestionCustomerAnswerService.getMonthCount());
        return Result.ok(indexVM);
    }
}
