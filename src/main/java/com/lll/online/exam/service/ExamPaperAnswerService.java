package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageRequestVM;
import com.lll.online.exam.viewmodel.admin.examPaperAnswer.ExamPaperAnswerPageResponseVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitVM;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageVM;

/**
 * (ExamPaperAnswer)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperAnswerService extends BaseService<ExamPaperAnswer>{

    ExamPaperAnswer selectByPidUid(Integer pid, Integer uid);

    Integer getAllCount();

    IPage<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM model);

    ExamPaperSubmitVM examPaperAnswerToVM(Integer id);

    String judge(ExamPaperSubmitVM examPaperSubmitVM);

    PageResult<ExamPaperAnswerPageResponseVM> adminPage(ExamPaperAnswerPageRequestVM model);

}