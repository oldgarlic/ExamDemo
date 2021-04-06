package com.lll.online.exam.controller.student;

import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.viewmodel.student.eduaction.SubjectVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/3
 */
@RequestMapping("api/student/education")
@RestController
public class EducationController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("subject/list")
    public Result<List<SubjectVM>> subjectList(){
        // TODO：根据用户的level来获取subject
        User user = getCurrentUser();
        List<Subject> subjects = subjectService.selectByLevel(user.getUserLevel());

        List<SubjectVM> list = subjects.stream().map(t -> {
            SubjectVM subjectVM = modelMapper.map(t, SubjectVM.class);
            subjectVM.setId(String.valueOf(t.getId()));
            return subjectVM;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }
}
