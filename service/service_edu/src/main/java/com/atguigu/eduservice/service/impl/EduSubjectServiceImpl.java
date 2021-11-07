package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    // 从excel中写入到数据库
    @Override
    public void saveEduSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取所有的subject
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 读取所有的OneSubject，一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 读取所有的TwoSubject，二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        List<OneSubject> list = new ArrayList<>();
        // 封装到最终返回的数据格式中
        for (EduSubject oneSubject : oneSubjectList) {
            OneSubject finalOneSubject = new OneSubject();
            // 一级分类,使用工具类
            BeanUtils.copyProperties(oneSubject, finalOneSubject);

            // 二级分类
            List<TwoSubject> twoFinalTwoSubjectList = new ArrayList<>();
            for (EduSubject twoSubject : twoSubjectList) {
                TwoSubject finalTwoSubject = new TwoSubject();
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    BeanUtils.copyProperties(twoSubject, finalTwoSubject);
                    twoFinalTwoSubjectList.add(finalTwoSubject);
                }
            }

            // 最终封装
            finalOneSubject.setChildren(twoFinalTwoSubjectList);
            list.add(finalOneSubject);
        }

        return list;
    }
}
