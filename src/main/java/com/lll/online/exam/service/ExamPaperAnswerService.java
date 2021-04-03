package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageVM;

import java.util.List;

/**
 * (ExamPaperAnswer)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperAnswerService extends BaseService<ExamPaperAnswer>{

    ExamPaperAnswer selectByPidUid(Integer pid, Integer uid);

    Integer getAllCount();

    IPage<ExamPaperAnswer> pageList(ExamPaperAnswerPageVM model);
}