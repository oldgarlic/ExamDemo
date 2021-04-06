package com.lll.online.exam.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperPageVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 试卷Controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@RestController
@RequestMapping("api/student/exam/paper")
public class ExamPaperController extends BaseController {
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private SubjectService subjectService;

    /*
    * @Description: 根据id获取试卷
    * @Param: Integer
    * @return: ExamPaperEditRequestVM
    * @Date: 2021/3/29
    */
    @PostMapping("select/{id}")
    public Result<ExamPaperEditRequestVM> select(@PathVariable Integer id){
        ExamPaperEditRequestVM examPaperEditRequestVM = examPaperService.selectByIdToVM(id);
        return Result.ok(examPaperEditRequestVM);
    }

    @PostMapping("pageList")
    public Result<PageResult<ExamPaperPageResponseVM>> pageList(@RequestBody ExamPaperPageVM model){
        IPage<ExamPaper> pageInfo = examPaperService.studentPageList(model);

        List<ExamPaperPageResponseVM> data = pageInfo.getRecords().stream().map(t -> {
            ExamPaperPageResponseVM map = modelMapper.map(t, ExamPaperPageResponseVM.class);
            map.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            map.setSubjectName(subjectService.selectById(t.getSubjectId()).getName());

            return map;
        }).collect(Collectors.toList());
        PageResult<ExamPaperPageResponseVM> result = new PageResult<>(data, pageInfo.getTotal(), pageInfo.getCurrent());
        return Result.ok(result);
    }
}
