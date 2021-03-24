package com.lll.online.exam.viewmodel.student.user;

import com.lll.online.exam.entity.User;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.BaseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前用户信息返回类
 *
 * @author: Mr.Garlic
 * @date: 2021/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseVM extends BaseVM {

    private Integer id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private Integer role;

    private Integer sex;

    private String birthDay;

    private String phone;

    private String lastActiveTime;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer userLevel;

    private String imagePath;

    public static UserResponseVM from(User user){
        UserResponseVM vm = modelMapper.map(user, UserResponseVM.class);
        vm.setBirthDay(DateTimeUtil.dateFormat(user.getBirthDay()));
        vm.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
        vm.setModifyTime(DateTimeUtil.dateFormat(user.getModifyTime()));
        vm.setLastActiveTime(DateTimeUtil.dateFormat(user.getLastActiveTime()));
        return vm;
    }
}