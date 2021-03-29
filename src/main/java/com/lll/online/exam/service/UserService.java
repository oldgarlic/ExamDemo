package com.lll.online.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.viewmodel.admin.user.UserPageRequestVM;
import com.lll.online.exam.viewmodel.admin.user.UserResponseVM;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:10
 */
public interface UserService extends BaseService<User>{

    User selectByUserName(String userName);
    Integer changeUserImage(User user,String imagePath);

    PageResult<UserResponseVM> selectUserPageList(UserPageRequestVM vm);

    Integer deleteById(Integer id);

    Integer changeStatus(Integer id);

    List<KeyValue> selectNameByUserName(String userName);

    List<User> selectUserByIds(List<Integer> receiveUserIds);
}