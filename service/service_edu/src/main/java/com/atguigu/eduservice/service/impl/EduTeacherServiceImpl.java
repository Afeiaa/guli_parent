package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    // 1. 分页查询讲师
    @Override
    public HashMap indexTeacherPage(long current, long limit) {
        Page<EduTeacher> page = new Page(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(page, wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        long currentPage = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        HashMap map = new HashMap<>();
        map.put("items", records);
        map.put("current", currentPage);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
