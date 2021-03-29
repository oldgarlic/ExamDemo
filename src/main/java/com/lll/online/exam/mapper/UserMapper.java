package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.other.KeyValue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:10
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<KeyValue> selectNameByUserName(String userName);
}