package com.lll.online.exam.entity.exam;

import com.lll.online.exam.entity.exam.ExamPaperQuestionItemObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperTitleItemObject {

    private String name;

    private List<ExamPaperQuestionItemObject> questionItems;
}
