package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.*;
import com.lll.online.exam.entity.enums.ActionEnum;
import com.lll.online.exam.entity.enums.ExamPaperAnswerStatusEnum;
import com.lll.online.exam.entity.enums.ExamPaperTypeEnum;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.entity.exam.ExamPaperQuestionItemObject;
import com.lll.online.exam.entity.exam.ExamPaperTitleItemObject;
import com.lll.online.exam.mapper.ExamPaperMapper;
import com.lll.online.exam.service.ExamPaperAnswerService;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.exam.*;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.student.dashboard.PaperFilter;
import com.lll.online.exam.viewmodel.student.dashboard.PaperInfo;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperPageVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ExamPaperAnswerService examPaperAnswerService;

    @Autowired
    public ExamPaperServiceImpl(ExamPaperAnswerService examPaperAnswerService, QuestionService questionService,ExamPaperMapper examPaperMapper,TextContentService textContentService) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
        this.textContentService = textContentService;
        this.questionService = questionService;
        this.examPaperAnswerService = examPaperAnswerService;
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
        examPaperEditRequestVM.setScore(ExamUtil.scoreToVM(examPaper.getScore()));

        if(examPaper.getPaperType()==ExamPaperTypeEnum.TimeLimit.getCode()){
            List<String> list = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            examPaperEditRequestVM.setLimitDateTime(list);
        }

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

    @Override
    public void updateTaskPaper(Integer taskId, List<Integer> paperIds) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",paperIds);
        List<ExamPaper> examPapers = examPaperMapper.selectList(queryWrapper);
        for (ExamPaper t : examPapers) {
            t.setTaskExamId(taskId);
        }
        UpdateWrapper<ExamPaper> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",paperIds).set("task_exam_id",taskId);
        examPaperMapper.update(null,updateWrapper);
    }

    @Override
    public void clearPaperIds(List<Integer> paperIds) {
        UpdateWrapper<ExamPaper> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",paperIds).set("task_exam_id",null);
        examPaperMapper.update(null,updateWrapper);
    }

    @Override
    public IPage<ExamPaper> taskExamPage(TaskExamPageRequestVM model) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("task_exam_id").eq("grade_level",model.getLevel()).eq("paper_type",model.getPaperType());
        Page<ExamPaper> examPaperPage = new Page<>(model.getPageIndex(), model.getPageSize());
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        IPage<ExamPaper> examPaperIPage = examPaperMapper.selectPage(examPaperPage, queryWrapper);
        return examPaperIPage;
    }

    @Override
    public List<PaperInfo> selectPaperInfo(PaperFilter paperFilter) {
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_type",paperFilter.getPaperType()).eq("grade_level",paperFilter.getGradeLevel());
        List<ExamPaper> examPapers = examPaperMapper.selectList(queryWrapper);

        List<PaperInfo> paperInfos = examPapers.stream().map(t -> {
            PaperInfo paperInfo = modelMapper.map(t, PaperInfo.class);

            return paperInfo;
        }).collect(Collectors.toList());

        return paperInfos;
    }

    @Transactional
    @Override
    public ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, User user) {
        // TODO：目的就是填满ExamPaperAnswerInfo的三个属性
        ExamPaperAnswerInfo examPaperAnswerInfo = new ExamPaperAnswerInfo();
        Date now = new Date();

        // 1. 设置ExamPaper
        ExamPaper examPaper = examPaperMapper.selectById(examPaperSubmitVM.getId());
        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(examPaper.getPaperType());
        // 如果过是任务试卷，根据试卷Id和用户Id查看是否已经做了
        if(examPaperTypeEnum == ExamPaperTypeEnum.Task){
            ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectByPidUid(examPaperSubmitVM.getId(), user.getId());
            if(examPaperAnswer!=null){return null;}
        }
        examPaperAnswerInfo.setExamPaper(examPaper);

        // 2. 设置List<ExamPaperQuestionCustomerAnswer>
        TextContent textContent = textContentService.selectById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(m -> m.getId())).collect(Collectors.toList());
        List<Question> questions = questionService.selectByIds(questionIds);
        // 转化为题目答案
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream().map(m -> {
                    Question question = questions.stream().filter(md -> md.getId().equals(m.getId())).findFirst().get();

                    ExamPaperSubmitItemVM examPaperSubmitItemVM = examPaperSubmitVM.getAnswerItems().stream()
                            .filter(tm -> tm.getQuestionId().equals(m.getId())).findFirst().orElse(null);
                    // TODO：未完待续
                    return createExamPaperQuestionCustomerAnswer(question, examPaperSubmitItemVM, examPaper, m.getItemOrder(), now, user);
                })).collect(Collectors.toList());
        examPaperAnswerInfo.setExamPaperQuestionCustomerAnswers(examPaperQuestionCustomerAnswers);

        // 3. 设置ExamPaperAnswer
        ExamPaperAnswer examPaperAnswer = ExamPaperAnswerFromVM(examPaperSubmitVM, examPaper, examPaperQuestionCustomerAnswers, user, now);
        examPaperAnswerInfo.setExamPaperAnswer(examPaperAnswer);
        return examPaperAnswerInfo;
    }

    @Override
    public IPage<ExamPaper> studentPageList(ExamPaperPageVM model) {
        Page<ExamPaper> page = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_type",model.getPaperType());
        if(model.getLevelId()!=null){
            queryWrapper.eq("grade_level",model.getLevelId());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        IPage<ExamPaper> examPaperIPage = examPaperMapper.selectPage(page, queryWrapper);
        return examPaperIPage;
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Integer id) {
        // 根据paperId去查ExamPaper
        ExamPaper examPaper = examPaperMapper.selectById(id);
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
        vm.setLevel(examPaper.getGradeLevel());

        // 根据frameTextContentId去得到question集合，它是以ExamPaperTitleItemObject的json形式写入数据库
        TextContent textContent = textContentService.selectById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), ExamPaperTitleItemObject.class);

        // 得到question的ids
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        // 获得子结果
        List<Question> questions = questionService.selectByIds(questionIds);
        //
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {

            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);

            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                // 找到对应的Id,这里直接获得不可以嘛，不是同一妈生的？？？
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question.getId());
                questionEditRequestVM.setItemOrder(i.getItemOrder());

                return questionEditRequestVM;
            }).collect(Collectors.toList());

            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());

        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        // 如果是时段试卷
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;

    }

    private ExamPaperAnswer ExamPaperAnswerFromVM(ExamPaperSubmitVM examPaperSubmitVM, ExamPaper examPaper, List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers, User user, Date now) {
        Integer systemScore = examPaperQuestionCustomerAnswers.stream().mapToInt(a -> a.getCustomerScore()).sum();
        long questionCorrect = examPaperQuestionCustomerAnswers.stream().filter(a -> a.getCustomerScore().equals(a.getQuestionScore())).count();
        ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
        examPaperAnswer.setPaperName(examPaper.getName());
        examPaperAnswer.setDoTime(examPaperSubmitVM.getDoTime());
        examPaperAnswer.setExamPaperId(examPaper.getId());
        examPaperAnswer.setCreateUser(user.getId());
        examPaperAnswer.setCreateTime(now);
        examPaperAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperAnswer.setQuestionCount(examPaper.getQuestionCount());
        examPaperAnswer.setPaperScore(examPaper.getScore());
        examPaperAnswer.setPaperType(examPaper.getPaperType());
        examPaperAnswer.setSystemScore(systemScore);
        examPaperAnswer.setUserScore(systemScore);
        examPaperAnswer.setTaskExamId(examPaper.getTaskExamId());
        examPaperAnswer.setQuestionCorrect((int) questionCorrect);
        boolean needJudge = examPaperQuestionCustomerAnswers.stream().anyMatch(d -> QuestionTypeEnum.needSaveTextContent(d.getQuestionType()));

        if (needJudge) {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }
        return examPaperAnswer;
    }

    private ExamPaperQuestionCustomerAnswer createExamPaperQuestionCustomerAnswer(Question question, ExamPaperSubmitItemVM examPaperSubmitItemVM,
                                                                                  ExamPaper examPaper, Integer itemOrder, Date now, User user) {
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = new ExamPaperQuestionCustomerAnswer();

        examPaperQuestionCustomerAnswer.setQuestionId(question.getId());
        examPaperQuestionCustomerAnswer.setExamPaperId(examPaper.getId());
        examPaperQuestionCustomerAnswer.setQuestionScore(question.getScore());
        examPaperQuestionCustomerAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperQuestionCustomerAnswer.setItemOrder(itemOrder);
        examPaperQuestionCustomerAnswer.setCreateTime(now);
        examPaperQuestionCustomerAnswer.setCreateUser(user.getId());
        examPaperQuestionCustomerAnswer.setQuestionType(question.getQuestionType());
        examPaperQuestionCustomerAnswer.setQuestionTextContentId(question.getInfoTextContentId());

        if (null == examPaperSubmitItemVM) {
            examPaperQuestionCustomerAnswer.setCustomerScore(0);
        } else {
            setSpecialFromVM(examPaperQuestionCustomerAnswer, question, examPaperSubmitItemVM);
        }
        return examPaperQuestionCustomerAnswer;
    }
    /**
     * 判断提交答案是否正确，保留用户提交的答案
     *
     * @param examPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer
     * @param question                        question
     * @param customerQuestionAnswer          customerQuestionAnswer
     */
    private void setSpecialFromVM(ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer, Question question, ExamPaperSubmitItemVM customerQuestionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.getQuestionTypeEnum(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getContent()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case MultipleChoice:
                String customerAnswer = ExamUtil.contentToString(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(customerAnswer);
                examPaperQuestionCustomerAnswer.setDoRight(customerAnswer.equals(question.getCorrect()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case GapFilling:
                String correctAnswer = JsonUtil.toJsonStr(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(correctAnswer);
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
            default:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
        }
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

}