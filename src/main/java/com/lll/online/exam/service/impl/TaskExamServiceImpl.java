package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.entity.TaskExam;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.task.TaskItemObject;
import com.lll.online.exam.mapper.TaskExamMapper;
import com.lll.online.exam.service.ExamPaperService;
import com.lll.online.exam.service.TaskExamService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskExamPageRequestVM;
import com.lll.online.exam.viewmodel.admin.taskExam.TaskRequestVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.attribute.standard.PrinterURI;
import java.util.List;
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
}