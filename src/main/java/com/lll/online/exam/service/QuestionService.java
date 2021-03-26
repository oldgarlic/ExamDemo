package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;

import java.util.List;

/**
 * (Question)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface QuestionService extends BaseService<Question>{


    Integer getAllCount();

    IPage<Question> pageList(QuestionPageRequestVM model);

    Integer insertQuestion(QuestionEditRequestVM model, User currentUser);

    Integer updateQuestion(QuestionEditRequestVM model);

    Integer deleteById(Integer id);

}