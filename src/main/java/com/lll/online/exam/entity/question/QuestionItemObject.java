package com.lll.online.exam.entity.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionItemObject {

    private String prefix;

    private String content;

    private Integer score;
}