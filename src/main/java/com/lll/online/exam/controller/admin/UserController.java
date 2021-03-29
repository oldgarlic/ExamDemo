package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.service.AuthenticationService;
import com.lll.online.exam.service.UserEventLogService;
import com.lll.online.exam.service.UserService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.admin.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户Controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@RestController("AdminUserController")
@RequestMapping("/api/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserEventLogService userEventLogService;

    /*
    * @Description: 根据用户名查询用户
    * @Param: String
    * @return: Result<List<KeyValue>>
    * @Date: 2021/3/28
    */
    @PostMapping("selectByUserName")
    public Result<List<KeyValue>> selectByUserName(@RequestBody String userName){
        List<KeyValue> keyValues = userService.selectNameByUserName(userName);
        return Result.ok(keyValues);
    }

    /*
    * @Description: 获取当前用户信息
    * @Param: []
    * @return: Result<UserResponseVM>
    * @Date: 2021/3/22
    */
    @PostMapping("current")
    public Result<UserResponseVM> currentUser(){
        User currentUser = getCurrentUser();
        UserResponseVM vm = UserResponseVM.fromUser(currentUser);
        return Result.ok(vm);
    }

    /*
    * @Description: 更新用户信息
    * @Param: UserUpdateVM
    * @return: Result
    * @Date: 2021/3/22
    */
    @PostMapping("update")
    public Result updateUser(@RequestBody @Valid UserUpdateVM vm){
        User currentUser = getCurrentUser();
        modelMapper.map(vm,currentUser);
        currentUser.setModifyTime(new Date());
        userService.update(currentUser);
        return Result.ok();
    }

    /*
    * @Description: 用户分页查询
    * @Param: UserPageRequestVM
    * @return: Result
    * @Date: 2021/3/22
    */
    @PostMapping("page/list")
    public Result<PageResult<UserResponseVM>> userPageList(@RequestBody UserPageRequestVM vm){

        PageResult<UserResponseVM> data = userService.selectUserPageList(vm);

        return Result.ok(data);
    }

    /*
    * @Description: 添加或修改用户信息
    * @Param: UserCreateVM
    * @return: Result
    * @Date: 2021/3/23
    */
    @PostMapping("edit")
    public Result editOrSaveUser(@RequestBody @Valid UserCreateVM vm){
        // TODO：①：根据id==null判断是新增还是修改，id==null情况再判断用户是否已经存在
        // TODO：②：依法进行新增/修改用户

        if(vm.getId()==null) {
            User exitUser = userService.selectByUserName(vm.getUserName());
            if(exitUser!=null){
                return new Result(2,"用户已存在");
            }
        }

        User user = modelMapper.map(vm, User.class);

        if(vm.getId()==null){
            user.setPassword(authenticationService.pwdEncode(user.getPassword()));
            user.setDeleted(false);
            user.setLastActiveTime(new Date());
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            userService.insert(user);
        }else{
            user.setPassword(authenticationService.pwdEncode(vm.getPassword()));
            user.setModifyTime(new Date());
            userService.update(user);
        }

        return Result.ok();
    }


    /*
    * @Description: 根据id获取User
    * @Param: Integer
    * @return: Result<UserResponseVM>
    * @Date: 2021/3/23
    */
    @PostMapping("select/{id}")
    public Result<UserResponseVM> getUserById(@PathVariable Integer id){
        User user = userService.selectById(id);
        if(user==null){
            return new Result<>(2,"用户不存在");
        }
        UserResponseVM userResponseVM = UserResponseVM.fromUser(user);
        return Result.ok(userResponseVM);
    }

    /*
    * @Description: 根据id逻辑删除User
    * @Param: Integer
    * @return: Result
    * @Date: 2021/3/23
    */
    @PostMapping("delete/{id}")
    public Result deleteUserById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.ok();
    }

    /*
    * @Description: 改变用户状态
    * @Param: Integer
    * @return: Result
    * @Date: 2021/3/23
    */
    @PostMapping("changeStatus/{id}")
    public Result changeStatus(@PathVariable Integer id){
        userService.changeStatus(id);
        return Result.ok();
    }

    /*
    * @Description: userEventLog的分页查询
    * @Param: []
    * @return: Result<PageResult<UserEventLogVM>>
    * @Date: 2021/3/23
    */
    @PostMapping("event/page/list")
    public Result<PageResult<UserEventLogVM>> userEventLogPageList(@RequestBody UserEventLogRequestVM vm){
        IPage<UserEventLog> pageInfo = userEventLogService.pageList(vm);
        List<UserEventLogVM> data = pageInfo.getRecords().stream().map(t->{
            UserEventLogVM map = modelMapper.map(t, UserEventLogVM.class);
            map.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            return map;
        }).collect(Collectors.toList());
        PageResult<UserEventLogVM> page = new PageResult<>(data,pageInfo.getTotal(),pageInfo.getCurrent());
        return Result.ok(page);
    }

}
