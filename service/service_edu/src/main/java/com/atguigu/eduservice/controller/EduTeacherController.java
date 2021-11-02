package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    // 1. 查询所有
    @GetMapping("/findall")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    // 2. 逻辑删除讲师
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable("id") String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag == false) {
            return R.error();
        }
        return R.ok();
    }

    // 3. 分页查询, 无条件
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();                     // 总的数量
        List<EduTeacher> records = pageTeacher.getRecords();     // 所有数据的集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }


}

