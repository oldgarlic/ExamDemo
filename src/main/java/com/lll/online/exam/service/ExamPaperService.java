package com.lll.online.exam.service;


import com.lll.online.exam.entity.ExamPaper;
import java.util.List;

/**
 * (ExamPaper)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperService extends BaseService<ExamPaper>{


    Integer getAllCount();
}