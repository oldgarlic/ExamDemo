package com.lll.online.exam.controller.student;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.TaskExam;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.ExamPaperTypeEnum;
import com.lll.online.exam.entity.task.TaskItemObject;
import com.lll.online.exam.service.ExamPaperQuestionCustomerAnswerService;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.TaskExamService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.viewmodel.student.dashboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@RestController
@RequestMapping("api/student/dashboard")
public class DashBoardController extends BaseController {

    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private TaskExamService taskExamService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    /*
    * @Description: 获取主页信息
    * @Param: []
    * @return: Result<IndexVM>
    * @Date: 2021/3/29
    */
    @PostMapping("index")
    public Result<IndexVM> index(){
        IndexVM indexVM = new IndexVM();
        User currentUser = getCurrentUser();
        PaperFilter paperFilter = new PaperFilter();
        paperFilter.setGradeLevel(currentUser.getUserLevel());
        // TODO：固定试卷
        paperFilter.setPaperType(ExamPaperTypeEnum.Fixed.getCode());
        List<PaperInfo> fixedPaper = examPaperService.selectPaperInfo(paperFilter);
        indexVM.setFixedPaper(fixedPaper);

        // TODO：时段试卷
        paperFilter.setPaperType(ExamPaperTypeEnum.TimeLimit.getCode());
        List<PaperInfo> pageInfo = examPaperService.selectPaperInfo(paperFilter);
        List<TimeLimitPaper> timeLimitPapers = pageInfo.stream().map(t -> {
            TimeLimitPaper timeLimitPaper = modelMapper.map(t, TimeLimitPaper.class);
            timeLimitPaper.setStartTime(DateTimeUtil.dateFormat(t.getLimitStartTime()));
            timeLimitPaper.setEndTime(DateTimeUtil.dateFormat(t.getLimitEndTime()));
            return timeLimitPaper;
        }).collect(Collectors.toList());
        indexVM.setTimeLimitPaper(timeLimitPapers);
        return Result.ok(indexVM);
    }

    /*
    * @Description: 获取当前属于当前用户的任务
    * @Param: []
    * @return: Result<TaskItemVm>
    * @Date: 2021/3/29
    */
    @PostMapping("task")
    public Result<List<TaskItemVm>> task(){
        User user = getCurrentUser();
        List<TaskExam> taskExams = taskExamService.selectByGradeLevel(user.getUserLevel());

        List<TaskItemVm> taskItemVms = taskExams.stream().map(t -> {
            TaskItemVm taskItemVm = modelMapper.map(t, TaskItemVm.class);
            List<TaskItemPaperVm> paperItems =getPaperItems(t.getFrameTextContentId());
            taskItemVm.setPaperItems(paperItems);
            return taskItemVm;
        }).collect(Collectors.toList());

        return Result.ok(taskItemVms);
    }

    public List<TaskItemPaperVm> getPaperItems(Integer textContentId){
        // TODO：status，examPaperAnswerId没写上，在哪儿呢,没答卷,不写
        TextContent textContent = textContentService.selectById(textContentId);
        List<TaskItemObject> taskItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class);
        List<TaskItemPaperVm> taskItemPaperVms = taskItemObjects.stream().map(t -> {
            TaskItemPaperVm taskItemPaperVm = modelMapper.map(t, TaskItemPaperVm.class);
            // taskItemPaperVm.setExamPaperAnswerId(0);
            // taskItemPaperVm.setStatus(0);
            return taskItemPaperVm;
        }).collect(Collectors.toList());

        return taskItemPaperVms;
    }
}
