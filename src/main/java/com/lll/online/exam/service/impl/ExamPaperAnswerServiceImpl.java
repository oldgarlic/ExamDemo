package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.*;
import com.lll.online.exam.entity.enums.ExamPaperAnswerStatusEnum;
import com.lll.online.exam.entity.enums.ExamPaperTypeEnum;
import com.lll.online.exam.entity.other.ExamPaperAnswerUpdate;
import com.lll.online.exam.entity.task.TaskItemAnswerObject;
import com.lll.online.exam.mapper.ExamPaperAnswerMapper;
import com.lll.online.exam.mapper.ExamPaperMapper;
import com.lll.online.exam.service.*;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageRequestVM;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageResponseVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitVM;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (ExamPaperAnswer)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperAnswerService")
public class ExamPaperAnswerServiceImpl extends BaseServiceImpl<ExamPaperAnswer> implements ExamPaperAnswerService {

    private ExamPaperAnswerMapper examPaperAnswerMapper;
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private ExamPaperMapper examPaperMapper;
    private TaskExamCustomerAnswerService taskExamCustomerAnswerService ;
    private TextContentService textContentService;
    private SubjectService subjectService;
    private ModelMapper modelMapper = ModelMapperUtil.instance();
    private UserService userService;

    @Autowired
    public ExamPaperAnswerServiceImpl(UserService userService, SubjectService subjectService, TextContentService textContentService, TaskExamCustomerAnswerService taskExamCustomerAnswerService,ExamPaperMapper examPaperMapper,ExamPaperAnswerMapper examPaperAnswerMapper,ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService) {
        super(examPaperAnswerMapper);
        this.examPaperAnswerMapper = examPaperAnswerMapper;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.examPaperMapper = examPaperMapper;
        this.taskExamCustomerAnswerService = taskExamCustomerAnswerService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @Override
    public ExamPaperAnswer selectByPidUid(Integer pid, Integer uid) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",pid).eq("create_user",uid);
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectOne(queryWrapper);
        return examPaperAnswer;
    }

    @Override
    public Integer getAllCount() {
        Integer count = examPaperAnswerMapper.selectCount(null);
        return count;
    }

    @Override
    public IPage<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM model) {
        Page<ExamPaperAnswer> page = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        if(model.getCreateUser()!=null){
            queryWrapper.eq("create_user",model.getCreateUser());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        IPage<ExamPaperAnswer> examPaperAnswerIPage = examPaperAnswerMapper.selectPage(page, queryWrapper);
        return examPaperAnswerIPage;
    }

    @Override
    public ExamPaperSubmitVM examPaperAnswerToVM(Integer id) {
        ExamPaperSubmitVM examPaperSubmitVM = new ExamPaperSubmitVM();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectById(id);
        examPaperSubmitVM.setId(examPaperAnswer.getId());
        examPaperSubmitVM.setDoTime(examPaperAnswer.getDoTime());
        examPaperSubmitVM.setScore(ExamUtil.scoreToVM(examPaperAnswer.getUserScore()));

        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerService.selectListByPaperAnswerId(examPaperAnswer.getId());

        List<ExamPaperSubmitItemVM> examPaperSubmitItemVMS = examPaperQuestionCustomerAnswers.stream()
                .map(a -> examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(a))
                .collect(Collectors.toList());
        examPaperSubmitVM.setAnswerItems(examPaperSubmitItemVMS);
        return examPaperSubmitVM;
    }

    @Transactional
    @Override
    public String judge(ExamPaperSubmitVM examPaperSubmitVM) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectById(examPaperSubmitVM.getId());
        List<ExamPaperSubmitItemVM> judgeItems = examPaperSubmitVM.getAnswerItems().stream().filter(d -> d.getDoRight() == null).collect(Collectors.toList());
        List<ExamPaperAnswerUpdate> examPaperAnswerUpdates = new ArrayList<>(judgeItems.size());
        Integer customerScore = examPaperAnswer.getUserScore();
        Integer questionCorrect = examPaperAnswer.getQuestionCorrect();
        for (ExamPaperSubmitItemVM d : judgeItems) {
            ExamPaperAnswerUpdate examPaperAnswerUpdate = new ExamPaperAnswerUpdate();
            examPaperAnswerUpdate.setId(d.getId());
            examPaperAnswerUpdate.setCustomerScore(ExamUtil.scoreFromVM(d.getScore()));
            boolean doRight = examPaperAnswerUpdate.getCustomerScore().equals(ExamUtil.scoreFromVM(d.getQuestionScore()));
            examPaperAnswerUpdate.setDoRight(doRight);
            examPaperAnswerUpdates.add(examPaperAnswerUpdate);
            customerScore += examPaperAnswerUpdate.getCustomerScore();
            if (examPaperAnswerUpdate.getDoRight()) {
                ++questionCorrect;
            }
        }
        examPaperAnswer.setUserScore(customerScore);
        examPaperAnswer.setQuestionCorrect(questionCorrect);
        examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        examPaperAnswerMapper.updateById(examPaperAnswer);
        examPaperQuestionCustomerAnswerService.updateScore(examPaperAnswerUpdates);

        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(examPaperAnswer.getPaperType());
        switch (examPaperTypeEnum) {
            case Task:
                //任务试卷批改完成后，需要更新任务的状态
                ExamPaper examPaper = examPaperMapper.selectById(examPaperAnswer.getExamPaperId());
                Integer taskId = examPaper.getTaskExamId();
                Integer userId = examPaperAnswer.getCreateUser();
                TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerService.getByTidUid(taskId, userId);
                TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
                List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
                taskItemAnswerObjects.stream()
                        .filter(d -> d.getExamPaperAnswerId().equals(examPaperAnswer.getId()))
                        .findFirst().ifPresent(taskItemAnswerObject -> taskItemAnswerObject.setStatus(examPaperAnswer.getStatus()));
                textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
                textContentService.update(textContent);
                break;
            default:
                break;
        }
        return ExamUtil.scoreToVM(customerScore);
    }

    @Override
    public PageResult<ExamPaperAnswerPageResponseVM> adminPage(ExamPaperAnswerPageRequestVM model) {
        Page<ExamPaperAnswer> examPaperAnswerPage = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        IPage<ExamPaperAnswer> examPaperAnswerIPage = examPaperAnswerMapper.selectPage(examPaperAnswerPage, queryWrapper);
        List<ExamPaperAnswerPageResponseVM> data = examPaperAnswerIPage.getRecords().stream().map(t -> {
            ExamPaperAnswerPageResponseVM map = modelMapper.map(t, ExamPaperAnswerPageResponseVM.class);
            map.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            map.setDoTime(ExamUtil.secondToVM(t.getDoTime()));
            map.setPaperScore(ExamUtil.scoreToVM(t.getPaperScore()));
            map.setUserScore(ExamUtil.scoreToVM(t.getUserScore()));
            map.setSubjectName(subjectService.selectById(t.getSubjectId()).getName());
            map.setUserName(userService.selectById(t.getCreateUser()).getUserName());
            map.setSystemScore(ExamUtil.scoreToVM(t.getSystemScore()));
            return map;
        }).collect(Collectors.toList());

        return new PageResult<>(data,examPaperAnswerIPage.getTotal(),examPaperAnswerIPage.getCurrent());
    }
}