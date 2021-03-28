package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.ActionEnum;
import com.lll.online.exam.entity.enums.ExamPaperTypeEnum;
import com.lll.online.exam.mapper.ExamPaperMapper;
import com.lll.online.exam.mapper.TextContentMapper;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.exam.*;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * (ExamPaper)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperService")
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService {

    private ExamPaperMapper examPaperMapper;
    private final ModelMapper modelMapper = ModelMapperUtil.instance();
    private TextContentService textContentService;
    private QuestionService questionService;

    @Autowired
    public ExamPaperServiceImpl( QuestionService questionService,ExamPaperMapper examPaperMapper,TextContentService textContentService) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
        this.textContentService = textContentService;
        this.questionService = questionService;
    }

    /*
    * @Description: 计算ExamPaper的总数
    * @Param: []
    * @return: java.lang.Integer
    * @Date: 2021/3/22
    */
    @Override
    public Integer getAllCount() {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        Integer count = examPaperMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public IPage<ExamPaper> pageList(ExamPaperPageRequestVM model) {
        Page<ExamPaper> page = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",false);
        if(model.getId()!=null){
            queryWrapper.eq("id",model.getId());
        }
        if(model.getLevel()!=null){
            queryWrapper.eq("grade_level",model.getLevel());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }

        IPage<ExamPaper> examPaperIPage = examPaperMapper.selectPage(page, queryWrapper);

        return examPaperIPage;
    }
    @Transactional
    @Override
    public ExamPaper saveOrEditExamPaper(ExamPaperEditRequestVM model, User currentUser) {
        // TODO：操作涉及到的表有t_exam_paper
        ActionEnum actionEnum = model.getId() == null ? ActionEnum.ADD : ActionEnum.EDIT;
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItems = model.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItems);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        ExamPaper examPaper;
        if(actionEnum == ActionEnum.ADD){
            // 添加操作
            examPaper = modelMapper.map(model, ExamPaper.class);
            TextContent textContent = new TextContent(frameTextContentStr, now);
            textContentService.insert(textContent);

            examPaper.setFrameTextContentId(textContent.getId());
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(currentUser.getId());
            examPaper.setDeleted(false);
            examPaperFromVM(model,examPaper,titleItems);

            examPaperMapper.insert(examPaper);

        }else{
            //修改操作
            examPaper = examPaperMapper.selectById(model.getId());
            TextContent textContent = textContentService.selectById(examPaper.getFrameTextContentId());
            textContent.setContent(frameTextContentStr);
            textContentService.update(textContent);

            modelMapper.map(model,examPaper);
            examPaperFromVM(model,examPaper,titleItems);
            examPaperMapper.updateById(examPaper);
        }

        return examPaper;
    }

    @Override
    public ExamPaperEditRequestVM selectByIdToVM(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectById(id);
        TextContent textContent = textContentService.selectById(examPaper.getFrameTextContentId());
        ExamPaperEditRequestVM examPaperEditRequestVM = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
        examPaperEditRequestVM.setLevel(examPaper.getGradeLevel());

        if(examPaper.getPaperType()==ExamPaperTypeEnum.TimeLimit.getCode()){
            List<String> list = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            examPaperEditRequestVM.setLimitDateTime(list);
        }
        // 这里出问题了？？？？ String转List对象
        // TextContent中的jsonString已 ExamPaperTitleItemObject 形式保存
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), ExamPaperTitleItemObject.class);

        // 设置List<ExamPaperTitleItemVM>
        List<ExamPaperTitleItemVM> list = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM vm = modelMapper.map(t, ExamPaperTitleItemVM.class);

            List<QuestionEditRequestVM> questionEditRequestVMS = t.getQuestionItems().stream().map(m -> {
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(m.getId());
                questionEditRequestVM.setItemOrder(m.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            vm.setQuestionItems(questionEditRequestVMS);
            return vm;
        }).collect(Collectors.toList());

        examPaperEditRequestVM.setTitleItems(list);

        return examPaperEditRequestVM;
    }

    @Override
    public Integer deleteById(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectById(id);
        examPaper.setDeleted(true);
        int count = examPaperMapper.updateById(examPaper);
        return count;
    }

    /*
    * @Description: 精简List<ExamPaperTitleItemVM>信息
    * @Date: 2021/3/27
    */
    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        List<ExamPaperTitleItemObject> list = titleItems.stream().map(t -> {
            ExamPaperTitleItemObject examPaperTitleItemObject = new ExamPaperTitleItemObject();
            examPaperTitleItemObject.setName(t.getName());
            List<ExamPaperQuestionItemObject> data = t.getQuestionItems().stream().map(m -> {
                ExamPaperQuestionItemObject examPaperQuestionItemObject = new ExamPaperQuestionItemObject();
                examPaperQuestionItemObject.setId(m.getId());
                examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                return examPaperQuestionItemObject;
            }).collect(Collectors.toList());
            examPaperTitleItemObject.setQuestionItems(data);

            return examPaperTitleItemObject;
        }).collect(Collectors.toList());
        return list;
    }

    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        // TODO：① 时段任务，需要设置开始时间和结束时间；②：设置总分数和总问题数量
        examPaper.setGradeLevel(examPaperEditRequestVM.getLevel());
        if(examPaperEditRequestVM.getPaperType() == ExamPaperTypeEnum.TimeLimit.getCode()){
            examPaper.setLimitStartTime(DateTimeUtil.parse(DateTimeUtil.NORMAL_FORMAT,examPaperEditRequestVM.getLimitDateTime().get(0)));
            examPaper.setLimitEndTime(DateTimeUtil.parse(DateTimeUtil.NORMAL_FORMAT,examPaperEditRequestVM.getLimitDateTime().get(1)));
        }

        Integer score = titleItemsVM.stream().flatMapToInt(t ->
                t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
        ).sum();

        Integer questionCount = titleItemsVM.stream().mapToInt(t -> t.getQuestionItems().size()).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);

    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        //map方法接受一个lambda表达式,对每个数据执行这个表达式
        int sum = list.stream().mapToInt(t -> {
            return t;
        }).sum();
        System.out.println(sum);


    }
}