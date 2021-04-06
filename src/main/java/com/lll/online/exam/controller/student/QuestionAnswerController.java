package com.lll.online.exam.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.ExamPaperQuestionCustomerAnswer;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.question.QuestionObject;
import com.lll.online.exam.service.ExamPaperQuestionCustomerAnswerService;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.HtmlUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.question.answer.QuestionAnswerVM;
import com.lll.online.exam.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import com.lll.online.exam.viewmodel.student.question.answer.QuestionPageStudentResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/3
 */
@RestController("StudentQuestionAnswerController")
@RequestMapping("api/student/question/answer")
public class QuestionAnswerController extends BaseController {

    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("page")
    public Result<PageResult<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model){
        model.setCreateUser(getCurrentUser().getId());
        IPage<ExamPaperQuestionCustomerAnswer> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);

        List<QuestionPageStudentResponseVM> data = pageInfo.getRecords().stream().map(t -> {
            QuestionPageStudentResponseVM map = modelMapper.map(t, QuestionPageStudentResponseVM.class);
            map.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            map.setSubjectName(subjectService.selectById(t.getSubjectId()).getName());
            TextContent textContent = textContentService.selectById(t.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            map.setShortTitle(clearHtml);
            return map;
        }).collect(Collectors.toList());
        PageResult<QuestionPageStudentResponseVM> pageResult = new PageResult<>(data, pageInfo.getTotal(), pageInfo.getCurrent());

        return Result.ok(pageResult);
    }

    @PostMapping("select/{id}")
    public Result<QuestionAnswerVM> select(@PathVariable Integer id){
        QuestionAnswerVM questionAnswerVM = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.selectById(id);
        ExamPaperSubmitItemVM examPaperSubmitItemVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        questionAnswerVM.setQuestionVM(questionEditRequestVM);
        questionAnswerVM.setQuestionAnswerVM(examPaperSubmitItemVM);

        return Result.ok(questionAnswerVM);
    }
}
