package com.lll.online.exam.controller.student;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

}
