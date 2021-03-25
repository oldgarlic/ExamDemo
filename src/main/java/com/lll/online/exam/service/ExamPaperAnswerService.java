package com.lll.online.exam.service;


import com.lll.online.exam.entity.ExamPaperAnswer;
import java.util.List;

/**
 * (ExamPaperAnswer)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperAnswerService extends BaseService<ExamPaperAnswer>{

    Integer getAllCount();
}