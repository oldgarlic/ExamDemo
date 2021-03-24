package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.ExamPaper;
import com.lll.online.exam.mapper.ExamPaperMapper;
import com.lll.online.exam.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ExamPaper)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperService")
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService {

    private ExamPaperMapper examPaperMapper;

    @Autowired
    public ExamPaperServiceImpl( ExamPaperMapper examPaperMapper) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
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
}