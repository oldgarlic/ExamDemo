package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.UserStatusEnum;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.mapper.UserMapper;
import com.lll.online.exam.service.UserService;
import com.lll.online.exam.viewmodel.admin.user.UserPageRequestVM;
import com.lll.online.exam.viewmodel.admin.user.UserResponseVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:11
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }

    @Override
    public User selectByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Integer changeUserImage(User user, String imagePath) {
        user.setImagePath(imagePath);
        return userMapper.updateById(user);
    }

    @Override
    public PageResult<UserResponseVM> selectUserPageList(UserPageRequestVM vm) {
        Page<User> page = new Page<>(vm.getPageIndex(), vm.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",vm.getRole());
        queryWrapper.eq("deleted",false);
        if(!StringUtils.isBlank(vm.getUserName())){
            queryWrapper.like("user_name",vm.getUserName());
        }
        IPage<User> userPage = userMapper.selectPage(page, queryWrapper);
        List<UserResponseVM> data= userPage.getRecords().stream().map(t -> {
            return UserResponseVM.fromUser(t);
        }).collect(Collectors.toList());
        PageResult<UserResponseVM> result = new PageResult<UserResponseVM>(data, userPage.getTotal(), userPage.getCurrent());

        return result;
    }

    @Override
    public Integer deleteById(Integer id) {
        User user = userMapper.selectById(id);
        user.setDeleted(true);
        userMapper.updateById(user);
        return null;
    }

    @Override
    public Integer changeStatus(Integer id) {
        User user = userMapper.selectById(id);
        if(user.getStatus()==UserStatusEnum.ENABLE.getCode()){
            user.setStatus(UserStatusEnum.DISABLE.getCode());
        }else {
            user.setStatus(UserStatusEnum.ENABLE.getCode());
        }
        userMapper.updateById(user);
        return null;
    }

    @Override
    public List<KeyValue> selectNameByUserName(String userName) {
        List<KeyValue> keyValues = userMapper.selectNameByUserName(userName);
        return keyValues;
    }

    @Override
    public List<User> selectUserByIds(List<Integer> receiveUserIds) {

        return userMapper.selectBatchIds(receiveUserIds);
    }
}