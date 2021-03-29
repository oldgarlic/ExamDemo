package com.lll.online.exam.controller.admin;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.Message;
import com.lll.online.exam.service.MessageService;
import com.lll.online.exam.viewmodel.admin.message.MessagePageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Message)表控制层
 *
 * @author oldGarlic
 * @since 2021-03-28 21:12:55
 */
@RestController("AdminMessageController")
@RequestMapping("api/admin/message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;
    /*
    * @Description: 消息的分页查询
    * @Param: MessagePageRequestVM
    * @return:
    * @Date: 2021/3/28
    */
    @PostMapping("page")
    public Result<PageResult<MessageResponseVM>> page(@RequestBody MessagePageRequestVM model){
        PageResult<MessageResponseVM> messageResponseVMPageResult = messageService.pageList(model);
        return Result.ok(messageResponseVMPageResult);
    }
    
    /*
    * @Description: 给用户发送消息
    * @Param: 
    * @return: 
    * @Date: 2021/3/28
    */
    @PostMapping("send")
    public Result sendMessage(@RequestBody MessageRequestVM model){
        // TODO：涉及到的表有：message，message_user
        messageService.sendMessage(model,getCurrentUser());
        return Result.ok();
    }
}