package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.viewmodel.admin.user.UserEventLogRequestVM;
import com.lll.online.exam.viewmodel.admin.user.UserEventLogVM;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (UserEventLog)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-18 11:05:51
 */
public interface UserEventLogService extends BaseService<UserEventLog>{

    List<UserEventLog> selectListById(Integer id);

    List<Integer> getMonthCount();

    IPage<UserEventLog> pageList(UserEventLogRequestVM vm);

}