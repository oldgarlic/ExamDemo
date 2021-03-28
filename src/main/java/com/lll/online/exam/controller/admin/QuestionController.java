package com.lll.online.exam.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.base.BaseController;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.base.Result;
import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.enums.QuestionStatusEnum;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.entity.question.QuestionObject;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.HtmlUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditItemVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionResponseVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

/**
 * QuestionController
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
    * @Description: 根据Id删除问题
    * @Param: Integer
    * @return: Result
    * @Date: 2021/3/26
    */
    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        questionService.deleteById(id);
        return Result.ok();
    }

    /*
    * @Description: 根据QuestionId来获取Question
    * @Param: Integer
    * @return: Result<QuestionEditRequestVM>
    * @Date: 2021/3/26
    */
    @PostMapping("select/{id}")
    public Result<QuestionEditRequestVM> selectById(@PathVariable Integer id){
        QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(id);
        return Result.ok(questionEditRequestVM);
    }


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
    public Result editOrSaveQuestion(@RequestBody @Valid QuestionEditRequestVM model){
        // TODO：①：检验不同题目要求；②：根据model.getId()==null来决定插入或修改Question；
        // 不同题目类型参数要求不同
        Result result = validQuestion(model);

        if(result.getCode()!= SystemCode.OK.getCode()){
            return result;
        }

        if(model.getId()==null){
            questionService.insertQuestion(model,getCurrentUser());
        }else{
            questionService.updateQuestion(model);
        }
        return Result.ok();
    }

    /*
    * @Description: 验证Question是否合规
    * @Param: QuestionEditRequestVM
    * @return: Result
    * @Date: 2021/3/26
    */
    public Result validQuestion(QuestionEditRequestVM model){
        // TODO：单选、判断、简单题需检验 correct 字段的notBlank，填空题需检验每填空处总分和预设总分是否一致
        // 单选或者判断题或者：必须存在题目答案
        Integer questionType = model.getQuestionType();
        if(questionType== QuestionTypeEnum.SingleChoice.getCode()
                ||questionType==QuestionTypeEnum.TrueFalse.getCode()
                || QuestionTypeEnum.ShortAnswer.getCode()==questionType){
            if(StringUtils.isBlank(model.getCorrect())){
                return new Result(2,"题目答案不能为空");
            }
        }
        // 填空题：计算每个空的分数和总分
        if(questionType==QuestionTypeEnum.GapFilling.getCode()){
            Integer sumScore = model.getItems().stream().mapToInt(t -> ExamUtil.scoreFromVM(t.getScore())).sum();
            Integer questionScore = ExamUtil.scoreFromVM(model.getScore());
            if(!sumScore.equals(questionScore)){
                return new Result(2,"题目总分和选项总分不同");
            }
        }
        return Result.ok();
    }
}
