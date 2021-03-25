package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.viewmodel.admin.education.SubjectEditRequestVM;
import com.lll.online.exam.viewmodel.admin.education.SubjectPageRequestVM;

import java.util.List;

/**
 * (Subject)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-24 21:12:20
 */
public interface SubjectService extends BaseService<Subject>{


    IPage<Subject> pageList(SubjectPageRequestVM model);


    Subject selectBySubject(Subject subject);

    Integer deleteById(Integer id);

    List<Subject> selectAllSubject();
}