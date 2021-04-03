package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.entity.TaskExamCustomerAnswer;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.task.TaskItemAnswerObject;
import com.lll.online.exam.mapper.TaskExamCustomerAnswerMapper;
import com.lll.online.exam.service.TaskExamCustomerAnswerService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (TaskExamCustomerAnswer)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-04-03 17:29:42
 */
@Service("taskExamCustomerAnswerService")
public class TaskExamCustomerAnswerServiceImpl extends BaseServiceImpl<TaskExamCustomerAnswer> implements TaskExamCustomerAnswerService {
    private TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
    private TextContentService textContentService;

    @Autowired
    public TaskExamCustomerAnswerServiceImpl(TextContentService textContentService,TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper) {
        super(taskExamCustomerAnswerMapper);
        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
        this.textContentService = textContentService;
    }

    @Override
    public void insertOrUpdate(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer, Date now) {
        Integer taskId = examPaper.getTaskExamId();
        Integer userId = examPaperAnswer.getCreateUser();
        TaskExamCustomerAnswer taskExamCustomerAnswer = getByTidUid(taskId, userId);
        if (null == taskExamCustomerAnswer) {
            taskExamCustomerAnswer = new TaskExamCustomerAnswer();
            taskExamCustomerAnswer.setCreateTime(now);
            taskExamCustomerAnswer.setCreateUser(userId);
            taskExamCustomerAnswer.setTaskExamId(taskId);
            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            TextContent textContent = textContentService.jsonConvertInsert(taskItemAnswerObjects, now, null);
            textContentService.insert(textContent);
            taskExamCustomerAnswer.setTextContentId(textContent.getId());
            insert(taskExamCustomerAnswer);
        } else {
            TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
            List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
            taskItemAnswerObjects.add(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
            textContentService.update(textContent);
        }
    }
    public TaskExamCustomerAnswer getByTidUid(Integer taskId,Integer userId){
        QueryWrapper<TaskExamCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_exam_id",taskId).eq("create_user",userId);
        TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.selectOne(queryWrapper);
        return taskExamCustomerAnswer;
    }
}