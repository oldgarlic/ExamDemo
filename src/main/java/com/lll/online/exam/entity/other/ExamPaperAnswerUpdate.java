package com.lll.online.exam.entity.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerUpdate {
    private Integer id;
    private Integer customerScore;
    private Boolean doRight;
}