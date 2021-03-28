package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperPageRequestVM;

import java.util.List;

/**
 * (ExamPaper)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperService extends BaseService<ExamPaper>{


    Integer getAllCount();

    IPage<ExamPaper> pageList(ExamPaperPageRequestVM model);

    ExamPaper saveOrEditExamPaper(ExamPaperEditRequestVM model, User currentUser);

    ExamPaperEditRequestVM selectByIdToVM(Integer id);

    Integer deleteById(Integer id);

}