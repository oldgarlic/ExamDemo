package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.TaskExam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TaskExam)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-27 22:58:14
 */
@Repository
public interface TaskExamMapper extends BaseMapper<TaskExam> {


}