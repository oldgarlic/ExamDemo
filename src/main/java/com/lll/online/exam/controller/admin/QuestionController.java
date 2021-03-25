package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.question.QuestionObject;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.HtmlUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@RestController("AdminQuestionController")
@RequestMapping("/api/admin/question")
public class QuestionController extends BaseController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TextContentService textContentService;

    /*
    * @Description: Question的分页查询
    * @Param: QuestionPageRequestVM
    * @return: Result<PageResult<QuestionResponseVM>>
    * @Date: 2021/3/24
    */
    @PostMapping("page")
    public Result<PageResult<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM model){
        IPage<Question> list = questionService.pageList(model);
        List<QuestionResponseVM> data = list.getRecords().stream().map(t->{
            QuestionResponseVM map = modelMapper.map(t, QuestionResponseVM.class);
            map.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            map.setScore(ExamUtil.scoreToVM(t.getScore()));
            //TODO：TextContent解析
            TextContent textContent = textContentService.selectById(t.getInfoTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);

            String clear = HtmlUtil.clear(questionObject.getTitleContent());
            map.setShortTitle(clear);

            return map;
        }).collect(Collectors.toList());
        PageResult<QuestionResponseVM> result = new PageResult<>(data, list.getTotal(), list.getCurrent());
        return Result.ok(result);
    }

    /*
    * @Description: 修改或添加Question
    * @Param: QuestionEditRequestVM
    * @return: Result
    * @Date: 2021/3/25
    */
    @PostMapping("edit")
    public Result editOrSaveQuestion(QuestionEditRequestVM model){
        // 题目类型不同参数要求不同

        if(model.getId()!=null){
            questionService.insertQuestion(model,getCurrentUser());
        }else{
            questionService.updateQuestion(model);
        }
        return Result.ok();
    }

}
