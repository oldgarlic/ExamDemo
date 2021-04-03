package com.lll.online.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.TaskExam;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskEditRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskExamPageRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskRequestVM;

import java.util.List;

/**
 * (TaskExam)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-27 22:58:14
 */
public interface TaskExamService extends BaseService<TaskExam>{


    IPage<TaskExam> page(TaskExamPageRequestVM model);

    TaskRequestVM taskExamByIdToVM(Integer id);

    void saveOrEdit(TaskEditRequestVM model, User currentUser);

    void deleteById(Integer id);

    List<TaskExam> selectByGradeLevel(Integer userLevel);
}