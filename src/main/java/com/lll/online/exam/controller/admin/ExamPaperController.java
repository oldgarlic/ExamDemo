package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import com.lll.online.exam.viewmodel.admin.exam.TaskExamPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷Controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
@RestController("AdminExamPaperController")
@RequestMapping("/api/admin/exam/paper")
public class ExamPaperController extends BaseController {
    @Autowired
    private ExamPaperService examPaperService;

    /*
    * @Description: 根据Id逻辑删除ExamPaper
    * @Param: Integer
    * @return: Result
    * @Date: 2021/3/27
    */
    @PostMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        examPaperService.deleteById(id);
        return Result.ok();
    }

    /*
    * @Description: 根据Id来查询ExamPaper
    * @Param: Integer
    * @return: Result<ExamPaperEditRequestVM>
    * @Date: 2021/3/27
    */
    @PostMapping("select/{id}")
    public Result<ExamPaperEditRequestVM> select(@PathVariable Integer id){
        ExamPaperEditRequestVM examPaperEditRequestVM = examPaperService.selectByIdToVM(id);
        return Result.ok(examPaperEditRequestVM);
    }

    /*
    * @Description: 添加或添加试卷
    * @Param: ExamPaperEditRequestVM
    * @return: Result
    * @Date: 2021/3/27
    */
    @PostMapping("edit")
    public Result<ExamPaperEditRequestVM> editOrSaveExamPaper(@RequestBody @Valid ExamPaperEditRequestVM model){
        // TODO：涉及到的表
        ExamPaper examPaper = examPaperService.saveOrEditExamPaper(model,getCurrentUser());
        return Result.ok();
    }


    /*
    * @Description: 试卷的分页查询
    * @Param: ExamPaperPageRequestVM
    * @return: Result<PageResult<ExamPaperResponseVM>>
     * @Date: 2021/3/27
    */
    @PostMapping("page")
    public Result<PageResult<ExamPaperResponseVM>> pageList(@RequestBody ExamPaperPageRequestVM model){
        IPage<ExamPaper> pageInfo = examPaperService.pageList(model);

        List<ExamPaperResponseVM> data = pageInfo.getRecords().stream().map(t -> {
            ExamPaperResponseVM examPaperResponseVM = modelMapper.map(t, ExamPaperResponseVM.class);
            examPaperResponseVM.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            // createTime
            return examPaperResponseVM;
        }).collect(Collectors.toList());

        PageResult<ExamPaperResponseVM> page = new PageResult<>(data, pageInfo.getTotal(), pageInfo.getCurrent());
        return Result.ok(page);
    }


    /*
    * @Description: 查询未在任务中的试卷
    * @Param: TaskExamPageRequestVM
    * @return: Result<PageResult<ExamPaperResponseVM>>
    * @Date: 2021/3/28
    */
    @PostMapping("taskExamPage")
    public Result<PageResult<ExamPaperResponseVM>> taskExamPage(@RequestBody TaskExamPageRequestVM model){
        IPage<ExamPaper> examPaperIPage = examPaperService.taskExamPage(model);
        List<ExamPaperResponseVM> list = examPaperIPage.getRecords().stream().map(t -> {
            ExamPaperResponseVM examPaperResponseVM = modelMapper.map(t, ExamPaperResponseVM.class);
            examPaperResponseVM.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            return examPaperResponseVM;
        }).collect(Collectors.toList());
        return Result.ok(new PageResult<>(list,examPaperIPage.getTotal(),examPaperIPage.getTotal()));
    }

}
