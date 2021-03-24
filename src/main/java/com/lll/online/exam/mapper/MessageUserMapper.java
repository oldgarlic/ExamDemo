package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.MessageUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MessageUser)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:13
 */
@Repository
public interface MessageUserMapper extends BaseMapper<MessageUser> {


}