package com.lll.online.exam.service;

import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.entity.TaskExamCustomerAnswer;

import java.util.Date;
import java.util.List;

/**
 * (TaskExamCustomerAnswer)表服务接口
 *
 * @author oldGarlic
 * @since 2021-04-03 17:29:42
 */
public interface TaskExamCustomerAnswerService extends BaseService<TaskExamCustomerAnswer>{


    void insertOrUpdate(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer, Date now);

    TaskExamCustomerAnswer getByTidUid(Integer taskId, Integer userId);
}