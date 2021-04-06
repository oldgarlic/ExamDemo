package com.lll.online.exam.viewmodel.student.exam;

import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查看试卷
 *
 * @author: Mr.Garlic
 * @date: 2021/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperReadVM {
    private ExamPaperEditRequestVM paper;
    private ExamPaperSubmitVM answer;
}