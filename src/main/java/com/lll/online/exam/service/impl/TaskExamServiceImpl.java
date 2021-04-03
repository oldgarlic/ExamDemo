package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.TaskExam;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.ActionEnum;
import com.lll.online.exam.entity.task.TaskItemObject;
import com.lll.online.exam.mapper.TaskExamMapper;
import com.lll.online.exam.mapper.TextContentMapper;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.TaskExamService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskEditRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskExamPageRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskRequestVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * (TaskExam)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-27 22:58:14
 */
@Service("taskExamService")
public class TaskExamServiceImpl extends BaseServiceImpl<TaskExam> implements TaskExamService {

    private TaskExamMapper taskExamMapper;
    private final ModelMapper modelMapper = ModelMapperUtil.instance();
    private TextContentService textContentService;
    private ExamPaperService examPaperService;

    @Autowired
    public TaskExamServiceImpl(TaskExamMapper taskExamMapper,TextContentService textContentService,ExamPaperService examPaperService ) {
        super(taskExamMapper);
        this.taskExamMapper = taskExamMapper;
        this.textContentService = textContentService;
        this.examPaperService = examPaperService;
    }

    @Override
    public IPage<TaskExam> page(TaskExamPageRequestVM model) {
        Page<TaskExam> taskExamPage = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<TaskExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",false);
        if(model.getGradeLevel()!=null){
            queryWrapper.eq("grade_level",model.getGradeLevel());
        }
        IPage<TaskExam> taskExamIPage = taskExamMapper.selectPage(taskExamPage, queryWrapper);
        return taskExamIPage;
    }

    @Override
    public TaskRequestVM taskExamByIdToVM(Integer id) {
        TaskExam taskExam = taskExamMapper.selectById(id);
        TaskRequestVM taskRequestVM = modelMapper.map(taskExam, TaskRequestVM.class);
        TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());

        List<TaskItemObject> taskItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class);

        List<ExamPaperResponseVM> examPaperResponseVMS = taskItemObjects.stream().map(t -> {
            ExamPaper examPaper = examPaperService.selectById(t.getExamPaperId());
            ExamPaperResponseVM examPaperResponseVM = modelMapper.map(examPaper, ExamPaperResponseVM.class);
            examPaperResponseVM.setCreateTime(DateTimeUtil.dateFormat(examPaper.getCreateTime()));
            return examPaperResponseVM;
        }).collect(Collectors.toList());

        taskRequestVM.setPaperItems(examPaperResponseVMS);

        return taskRequestVM;
    }

    @Transactional
    @Override
    public void saveOrEdit(TaskEditRequestVM model, User currentUser) {
        // TODO：涉及的表有：task_exam and text_content(TaskItemObject的形式写入) and exam
        ActionEnum actionEnum = model.getId() == null ? ActionEnum.ADD : ActionEnum.EDIT;
        Date now = new Date();
        TaskExam taskExam;
        if(actionEnum==actionEnum.ADD) {
            // ①：TextContent的操作
            AtomicInteger atomicInteger = new AtomicInteger(1);
            List<TaskItemObject> taskItemObjects = model.getPaperItems().stream().map(t -> {
                TaskItemObject taskItemObject = modelMapper.map(t, TaskItemObject.class);
                taskItemObject.setItemOrder(atomicInteger.getAndIncrement());
                return taskItemObject;
            }).collect(Collectors.toList());
            TextContent textContent = new TextContent();
            textContent.setCreateTime(now);
            textContent.setContent(JsonUtil.toJsonStr(taskItemObjects));
            textContentService.insert(textContent);
            // ②：taskExam的操作
            taskExam = modelMapper.map(model, TaskExam.class);
            taskExam.setCreateTime(now);
            taskExam.setCreateUser(currentUser.getId());
            taskExam.setCreateUserName(currentUser.getUserName());
            taskExam.setDeleted(false);
            taskExam.setFrameTextContentId(textContent.getId());
            taskExamMapper.insert(taskExam);
        }else{
            taskExam = taskExamMapper.selectById(model.getId());
            TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
            AtomicInteger atomicInteger = new AtomicInteger(1);
            List<TaskItemObject> taskItemObjects = model.getPaperItems().stream().map(t -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(t.getId());
                taskItemObject.setExamPaperName(t.getName());
                taskItemObject.setItemOrder(atomicInteger.getAndIncrement());
                return taskItemObject;
            }).collect(Collectors.toList());

            textContent.setCreateTime(now);
            textContent.setContent(JsonUtil.toJsonStr(taskItemObjects));
            textContentService.update(textContent);

            modelMapper.map(model,taskExam);
            taskExamMapper.updateById(taskExam);
            // 清除exam中ids
            List<Integer> paperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class)
                    .stream().map(t -> t.getExamPaperId()).collect(Collectors.toList());

            examPaperService.clearPaperIds(paperIds);

        }
        List<Integer> ids = model.getPaperItems().stream().map(t ->t.getId()).collect(Collectors.toList());
        examPaperService.updateTaskPaper(taskExam.getId(),ids);
    }

    @Override
    public void deleteById(Integer id) {
        TaskExam taskExam = taskExamMapper.selectById(id);
        taskExam.setDeleted(true);
        taskExamMapper.updateById(taskExam);
    }

    @Override
    public List<TaskExam> selectByGradeLevel(Integer userLevel) {
        QueryWrapper<TaskExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade_level",userLevel);
        List<TaskExam> taskExams = taskExamMapper.selectList(queryWrapper);
        return taskExams;
    }
}