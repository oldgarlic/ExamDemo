package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Subject)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-24 21:12:20
 */
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {

}