package com.lll.online.exam.controller.student;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.entity.enums.RoleEnum;
import com.lll.online.exam.entity.enums.UserStatusEnum;
import com.lll.online.exam.event.UserEvent;
import com.lll.online.exam.service.AuthenticationService;
import com.lll.online.exam.service.MessageService;
import com.lll.online.exam.service.UserEventLogService;
import com.lll.online.exam.service.UserService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.student.message.MessageRequestVM;
import com.lll.online.exam.viewmodel.student.message.MessageResponseVM;
import com.lll.online.exam.viewmodel.student.user.UserEventLogVM;
import com.lll.online.exam.viewmodel.student.user.UserRegisterVM;
import com.lll.online.exam.viewmodel.student.user.UserResponseVM;
import com.lll.online.exam.viewmodel.student.user.UserUpdateVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * (User)表控制层
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:11
 */
@RestController
@RequestMapping("/api/student/user")
public class UserController extends BaseController {

    @Autowired
    private UserEventLogService userEventLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthenticationService authenticationService;


    /*
    * @Description: 阅读消息
    * @Param: Integer：message_user表的id
    * @return: Result
    * @Date: 2021/4/4
    */
    @PostMapping("message/read/{id}")
    public Result readMessage(@PathVariable Integer id){
        User user = getCurrentUser();
        messageService.readMessage(id,user);
        return Result.ok();
    }


    /*
    * @Description: 消息分页
    * @Param: MessageRequestVM
    * @return: Result<PageResult<MessageResponseVM>>
    * @Date: 2021/4/4
    */
    @PostMapping("message/page")
    public Result<PageResult<MessageResponseVM>> page(@RequestBody MessageRequestVM messageRequestVM){
        messageRequestVM.setReceiveUserId(getCurrentUser().getId());
        PageResult<MessageResponseVM>  pageResult = messageService.studentPage(messageRequestVM);
        return Result.ok(pageResult);
    }


    /**
    * @Description: 查询当前用户信息
    * @Param: []
    * @return: Result<UserResponseVM>
    * @Date: 2021/3/20
    */
    @PostMapping("current")
    public Result<UserResponseVM> CurrentUser(){
        // TODO：① 获取当前用户信息；②：使用modelMapper和DateUtil工具类把类型转换下
        User currentUser = webContent.getCurrentUser();
        UserResponseVM vm = UserResponseVM.from(currentUser);
        return Result.ok(vm);
    }
    /*
     * @Description:用户事件日志获取
     * @Param: []
     * @return: Result<List<UserEventLogVM>>
     * @Date: 2021/3/21
     */
    @PostMapping("log")
    public Result<List<UserEventLogVM>> userEventLog(){
        //TODO：①：获取用户信息,user-userEventLog表的关联键位userId，Date格式处理下返回
        User currentUser = webContent.getCurrentUser();
        List<UserEventLog> userEventLogs = userEventLogService.selectListById(currentUser.getId());
        List<UserEventLogVM> vms = userEventLogs.stream().map(t -> {
            UserEventLogVM vm = modelMapper.map(t, UserEventLogVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());
        return Result.ok(vms);
    }

    /*
     * @Description: 更新用户信息
     * @Param: [vm]
     * @return: com.lll.online.exam.base.Result
     * @Date: 2021/3/21
     */
    @PostMapping("update")
    public Result updateUser(@RequestBody @Valid UserUpdateVM vm){
        // TODO：①更新用户信息；②记录用户更新日志
        if(StringUtils.isBlank(vm.getBirthDay())){
            vm.setBirthDay(null);
        }
        // 这个查询无所谓selectById或者selectByUsername
        User user = userService.selectByUserName(getCurrentUser().getUserName());
        modelMapper.map(vm,user);
        user.setModifyTime(new Date());
        // 怕vm传过来一些数据为null，污染数据库中数据
        // 在已存在的数据上，mybatis-plus默认不更新空字符串和null
        userService.update(user);

        UserEventLog userEventLog = new UserEventLog(user.getId(),user.getUserName(),user.getRealName(),new Date());
        userEventLog.setContent(user.getUserName()+"更新了用户信息");
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return Result.ok();
    }

    /*
     * @Description:查询未读用户信息
     * @Param: []
     * @return: Result<Integer>
     * @Date: 2021/3/21
     */
    @PostMapping("message/unreadCount")
    public Result<Integer> unreadCount(){
        Integer count = messageService.unReadCount(getCurrentUser().getId());
        return Result.ok(count);
    }


    /*
    * @Description: 用户注册
    * @Param: [vm]
    * @return: com.lll.online.exam.base.Result
    * @Date: 2021/3/21
    */
    @PostMapping("register")
    public Result register(@RequestBody @Valid UserRegisterVM vm){
        //TODO：①：校验用户是否已存在;②：补成一个完整User；③发布事件：写入用户注册时间
        User existUser = userService.selectByUserName(vm.getUserName());
        if(existUser!=null){
            return new Result(2,"用户已存在");
        }

        User user = modelMapper.map(vm, User.class);
        user.setUserUuid(UUID.randomUUID().toString());
        user.setPassword(authenticationService.pwdEncode(vm.getPassword()));
        user.setCreateTime(new Date());
        user.setRole(RoleEnum.STUDENT.getCode());
        user.setStatus(UserStatusEnum.ENABLE.getCode());
        user.setLastActiveTime(new Date());
        user.setDeleted(false);
        userService.insert(user);

        UserEventLog userEventLog = new UserEventLog(user.getId(),user.getUserName(),user.getRealName(),new Date());
        userEventLog.setContent("欢迎"+user.getUserName()+"注册在线考试系统");
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return Result.ok();
    }
}