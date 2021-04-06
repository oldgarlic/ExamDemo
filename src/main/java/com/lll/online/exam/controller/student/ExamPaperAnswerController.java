package com.lll.online.exam.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.*;
import com.lll.online.exam.entity.enums.ExamPaperAnswerStatusEnum;
import com.lll.online.exam.event.CalculateExamPaperAnswerCompleteEvent;
import com.lll.online.exam.event.UserEvent;
import com.lll.online.exam.service.ExamPaperAnswerService;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperReadVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitVM;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷答案controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/3
 */
@RestController
@RequestMapping("api/student/exampaper/answer")
public class ExamPaperAnswerController extends BaseController {

    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private SubjectService subjectService;

    /*
    * @Description: 批改试卷
    * @Param: ExamPaperSubmitVM
    * @return: Result
    * @Date: 2021/4/4
    */
    @PostMapping("edit")
    public Result edit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM){
        boolean notJudge = examPaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if (notJudge) {
            return Result.fail(2, "有未批改题目");
        }

        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(examPaperSubmitVM.getId());
        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
        if (examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete) {
            return Result.fail(3, "试卷已完成");
        }
        String score = examPaperAnswerService.judge(examPaperSubmitVM);
        User user = getCurrentUser();
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + score;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return Result.ok(score);
    }


    /*
    * @Description: 阅读答卷
    * @Param: Integer
    * @return: Result<ExamPaperReadVM>
    * @Date: 2021/4/4
    */
    @PostMapping("read/{id}")
    public Result<ExamPaperReadVM> read(@PathVariable Integer id){
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(id);
        ExamPaperReadVM examPaperReadVM = new ExamPaperReadVM();

        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        examPaperReadVM.setAnswer(answer);
        examPaperReadVM.setPaper(paper);

        return Result.ok(examPaperReadVM);
    }

    /*
     * @Description: 做试卷
     * @Param: ExamPaperSubmitVM
     * @return: Result
     * @Date: 2021/4/3
     */
    @PostMapping("answerSubmit")
    public Result submitAnswer(@RequestBody ExamPaperSubmitVM examPaperSubmitVM){
        User user = getCurrentUser();
        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperService.calculateExamPaperAnswer(examPaperSubmitVM,user);
        if(examPaperAnswerInfo==null){
            return Result.fail(2,"任务试卷只能做一次");
        }

        UserEventLog userEventLog = new UserEventLog(user.getId(),user.getUserName(),user.getRealName(),new Date());
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        String userScore = ExamUtil.scoreToVM(examPaperAnswer.getUserScore());
        String doTime = ExamUtil.secondToVM(examPaperAnswer.getDoTime());
        String content = user.getUserName()+"提交了试卷："+examPaperAnswerInfo.getExamPaper().getName()+"得分"+userScore
                +"耗时："+doTime;
        userEventLog.setContent(content);

        eventPublisher.publishEvent(new UserEvent(userEventLog));
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));

        return Result.ok(userScore);
    }

    @PostMapping("pageList")
    public Result<PageResult<ExamPaperAnswerPageResponseVM>> pageList(@RequestBody ExamPaperAnswerPageVM model){
        model.setCreateUser(getCurrentUser().getId());
        IPage<ExamPaperAnswer> pageInfo = examPaperAnswerService.studentPage(model);

        List<ExamPaperAnswerPageResponseVM> data = pageInfo.getRecords().stream().map(t -> {
            ExamPaperAnswerPageResponseVM examPaperAnswerPageResponseVM = modelMapper.map(t, ExamPaperAnswerPageResponseVM.class);
            examPaperAnswerPageResponseVM.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            Subject subject = subjectService.selectById(t.getSubjectId());
            examPaperAnswerPageResponseVM.setSubjectName(subject.getName());
            examPaperAnswerPageResponseVM.setDoTime(ExamUtil.secondToVM(t.getDoTime()));
            examPaperAnswerPageResponseVM.setUserName(getCurrentUser().getUserName());
            examPaperAnswerPageResponseVM.setSystemScore(ExamUtil.scoreToVM(t.getSystemScore()));
            examPaperAnswerPageResponseVM.setPaperScore(ExamUtil.scoreToVM(t.getPaperScore()));
            examPaperAnswerPageResponseVM.setUserScore(ExamUtil.scoreToVM(t.getUserScore()));
            return examPaperAnswerPageResponseVM;
        }).collect(Collectors.toList());
        PageResult<ExamPaperAnswerPageResponseVM> pageResult = new PageResult<>(data, pageInfo.getTotal(), pageInfo.getCurrent());
        return Result.ok(pageResult);
    }
}
