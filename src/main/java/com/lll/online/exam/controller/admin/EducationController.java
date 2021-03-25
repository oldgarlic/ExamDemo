package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.viewmodel.admin.education.SubjectEditRequestVM;
import com.lll.online.exam.viewmodel.admin.education.SubjectPageRequestVM;
import com.lll.online.exam.viewmodel.admin.education.SubjectResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学科Controller
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@RestController("AdminEducationController")
@RequestMapping("/api/admin/education")
public class EducationController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    /*
    * @Description: 列出所有学科
    * @Param: []
    * @return: List<Subject>
    * @Date: 2021/3/25
    */
    @PostMapping("subject/list")
    public Result<List<Subject>>list(){
        List<Subject> list = subjectService.selectAllSubject();
        return Result.ok(list);
    }

    /*
    * @Description: subject分页查询
    * @Param: SubjectPageRequestVM
    * @return: Result<PageResult<SubjectResponseVM>>
    * @Date: 2021/3/24
    */
    @PostMapping("subject/page")
    public Result<PageResult<SubjectResponseVM>> pageList(@RequestBody SubjectPageRequestVM model){
        IPage<Subject> subjectIPage = subjectService.pageList(model);
        List<SubjectResponseVM> data = subjectIPage.getRecords().stream().map(t -> {
            SubjectResponseVM map = modelMapper.map(t, SubjectResponseVM.class);
            return map;
        }).collect(Collectors.toList());
        PageResult<SubjectResponseVM> result = new PageResult<>(data, subjectIPage.getTotal(), subjectIPage.getCurrent());
        return Result.ok(result);
    }

    /*
    * @Description: 添加或修改Subject
    * @Param: SubjectEditRequestVM
    * @return: Result
    * @Date: 2021/3/24
    */
    @PostMapping("subject/edit")
    public Result edit(@RequestBody SubjectEditRequestVM model){
        Subject subject = modelMapper.map(model, Subject.class);
        if(subject.getId()==null){
            Subject exitSubject = subjectService.selectBySubject(subject);
            if(exitSubject!=null){
                return new Result(2,"科目已存在");
            }else {
                subject.setDeleted(false);
                subjectService.insert(subject);
            }
        }else{
            subjectService.update(subject);
        }
        return Result.ok();
    }

    /*
    * @Description: 根据Id查询Subject
    * @Param: Integer
    * @return: Result<SubjectResponseVM>
     * @Date: 2021/3/24
    */
    @PostMapping("subject/select/{id}")
    public Result<SubjectResponseVM> selectById(@PathVariable Integer id){
        Subject subject = subjectService.selectById(id);
        SubjectResponseVM map = modelMapper.map(subject, SubjectResponseVM.class);
        return Result.ok(map);
    }

    /*
    * @Description: 根据Id逻辑删除Subject
    * @Param: Integer
    * @return: Result
    * @Date: 2021/3/24
    */
    @PostMapping("subject/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        subjectService.deleteById(id);
        return Result.ok();
    }
}
