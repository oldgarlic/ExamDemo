package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.mapper.SubjectMapper;
import com.lll.online.exam.service.SubjectService;
import com.lll.online.exam.viewmodel.admin.education.SubjectEditRequestVM;
import com.lll.online.exam.viewmodel.admin.education.SubjectPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Subject)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-24 21:12:20
 */
@Service("subjectService")
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements SubjectService {

    private  SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper) {
        super(subjectMapper);
        this.subjectMapper = subjectMapper;
    }

    @Override
    public IPage<Subject> pageList(SubjectPageRequestVM model) {
        Page<Subject> page = new Page<Subject>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",false);
        if(model.getId()!=null){
            queryWrapper.eq("id",model.getId());
        }
        if(model.getLevel()!=null){
            queryWrapper.eq("level",model.getLevel());
        }
        IPage<Subject> subjectIPage = subjectMapper.selectPage(page, queryWrapper);
        return subjectIPage;
    }

    @Override
    public Subject selectBySubject(Subject subject) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",subject.getLevel()).eq("name",subject.getName());
        Subject data = subjectMapper.selectOne(queryWrapper);
        return data;
    }

    @Override
    public Integer deleteById(Integer id) {
        Subject subject = subjectMapper.selectById(id);
        subject.setDeleted(true);
        int back = subjectMapper.updateById(subject);
        return back;
    }

    @Override
    public List<Subject> selectAllSubject() {

        return subjectMapper.selectList(null);
    }

    @Override
    public List<Subject> selectByLevel(Integer userLevel) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",userLevel);
        List<Subject> subjects = subjectMapper.selectList(queryWrapper);
        return subjects;
    }

}