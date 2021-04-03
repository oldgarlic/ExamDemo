package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.ExamPaperAnswerInfo;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.TaskExamPageRequestVM;
import com.lll.online.exam.viewmodel.student.dashboard.PaperFilter;
import com.lll.online.exam.viewmodel.student.dashboard.PaperInfo;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitVM;

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

    void updateTaskPaper(Integer taskId, List<Integer> paperIds);

    void clearPaperIds(List<Integer> paperIds);

    IPage<ExamPaper> taskExamPage(TaskExamPageRequestVM model);

    List<PaperInfo> selectPaperInfo(PaperFilter paperFilter);

    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user);
}