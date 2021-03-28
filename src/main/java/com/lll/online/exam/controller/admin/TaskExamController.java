package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.TaskExam;
import com.lll.online.exam.service.TaskExamService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskExamPageRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskPageResponseVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (TaskExam)表控制层
 *
 * @author oldGarlic
 * @since 2021-03-27 22:58:14
 */
@RestController("AdminTaskExamController")
@RequestMapping("api/admin/task")
public class TaskExamController extends BaseController {
    @Autowired
    private TaskExamService taskExamService;
    /*
    * @Description: TaskExam的分页查询
    * @Param: TaskExamPageRequestVM
    * @return: Result<PageResult<TaskPageResponseVM>>
    * @Date: 2021/3/27
    */
    @PostMapping("page")
    public Result<PageResult<TaskPageResponseVM>> page(@RequestBody  TaskExamPageRequestVM model){
        IPage<TaskExam> taskExamIPage = taskExamService.page(model);
        List<TaskPageResponseVM> taskPageResponseVMS = taskExamIPage.getRecords().stream().map(t -> {
            TaskPageResponseVM taskPageResponseVM = modelMapper.map(t, TaskPageResponseVM.class);
            taskPageResponseVM.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            return taskPageResponseVM;
        }).collect(Collectors.toList());
        return Result.ok(new PageResult<>(taskPageResponseVMS,taskExamIPage.getTotal(),taskExamIPage.getCurrent()));
    }

    /*
    * @Description: 根据id来查询TaskExam
    * @Param: Integer
    * @return: Result<TaskRequestVM>
    * @Date: 2021/3/27
    */
    @PostMapping("select/{id}")
    public Result<TaskRequestVM> select(@PathVariable Integer id){
        TaskRequestVM vm =taskExamService.taskExamByIdToVM(id);

        return Result.ok(vm);
    }
}