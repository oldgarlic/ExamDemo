package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.mapper.UserEventLogMapper;
import com.lll.online.exam.service.UserEventLogService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.admin.user.UserEventLogRequestVM;
import com.lll.online.exam.viewmodel.admin.user.UserEventLogVM;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (UserEventLog)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-18 11:05:51
 */
@Service("userEventLogService")
public class UserEventLogServiceImpl extends BaseServiceImpl<UserEventLog> implements UserEventLogService {

    @Autowired
    private UserEventLogMapper userEventLogMapper;


    public UserEventLogServiceImpl(UserEventLogMapper userEventLogMapper) {
        super(userEventLogMapper);
        this.userEventLogMapper = userEventLogMapper;
    }

    @Override
    public List<UserEventLog> selectListById(Integer id) {
        QueryWrapper<UserEventLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<UserEventLog> list = userEventLogMapper.selectList(queryWrapper);
        return list;
    }

    /*
     * @Description: 返回一个从当月第一天到今天的集合，Integer为对应日期的活跃量
     * @Param: []
     * @return: java.util.List<java.lang.Integer>
     * @Date: 2021/3/22
     */
    @Override
    public List<Integer> getMonthCount() {
        Date startDate = DateTimeUtil.getMonthStartDate();
        Date endDate = DateTimeUtil.getMonthEndDate();
        List<KeyValue> list = userEventLogMapper.selectCountByDate(startDate,endDate);
        // 这里
        List<String> startToNowDate = DateTimeUtil.getStartToNowDate();
        List<Integer> result =  startToNowDate.stream().map(md -> {
            KeyValue keyValue = list.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public IPage<UserEventLog> pageList(UserEventLogRequestVM vm) {
        Page<UserEventLog> page = new Page<>(vm.getPageIndex(), vm.getPageSize());
        QueryWrapper<UserEventLog> queryWrapper = new QueryWrapper<>();
        if(null!=vm.getUserId()){
            queryWrapper.eq("user_id",vm.getUserId());
        }
        if(!StringUtils.isBlank(vm.getUserName())){
            queryWrapper.like("user_name",vm.getUserName());
        }
        IPage<UserEventLog> result = userEventLogMapper.selectPage(page, queryWrapper);

        return result;
    }


}