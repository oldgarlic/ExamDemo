package com.lll.online.exam.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Message)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:48
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {


}