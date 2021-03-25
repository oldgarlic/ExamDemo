package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.entity.other.KeyValue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * (UserEventLog)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-18 11:05:51
 */
@Repository
public interface UserEventLogMapper extends BaseMapper<UserEventLog> {

    List<KeyValue> selectCountByDate(Date startDate, Date endDate);
}