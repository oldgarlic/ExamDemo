package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.ExamPaperQuestionCustomerAnswer;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.mapper.ExamPaperQuestionCustomerAnswerMapper;
import com.lll.online.exam.service.ExamPaperQuestionCustomerAnswerService;
import com.lll.online.exam.utility.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (ExamPaperQuestionCustomerAnswer)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperQuestionCustomerAnswerService")
public class ExamPaperQuestionCustomerAnswerServiceImpl extends BaseServiceImpl<ExamPaperQuestionCustomerAnswer> implements ExamPaperQuestionCustomerAnswerService {

    private ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper;
    @Autowired
    public ExamPaperQuestionCustomerAnswerServiceImpl(ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper) {
        super(examPaperQuestionCustomerAnswerMapper);
        this.examPaperQuestionCustomerAnswerMapper = examPaperQuestionCustomerAnswerMapper;
    }

    @Override
    public Integer getAllCount() {
        return examPaperQuestionCustomerAnswerMapper.selectCount(null);
    }

    /*
    * @Description: 获得日答题数
    * @Param: []
    * @return: java.util.List<java.lang.Integer>
    * @Date: 2021/3/22
    */
    @Override
    public List<Integer> getMonthCount() {
        Date startDate = DateTimeUtil.getMonthStartDate();
        Date endDate = DateTimeUtil.getMonthEndDate();
        // TODO：sql写得有问题？？？
        List<KeyValue> list = examPaperQuestionCustomerAnswerMapper.selectCountByDate(startDate,endDate);
        List<String> startToNowDate = DateTimeUtil.getStartToNowDate();

        List<Integer> result =  startToNowDate.stream().map(md -> {
            KeyValue keyValue = list.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());

        return result;
    }
}