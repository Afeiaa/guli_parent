package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    // 1. 查询所有
    @GetMapping("/findall")
    public R findAll() {
        try {
            int age = 10/0;
        } catch (Exception e) {
            throw new GuliException(20001, "抛出了GuliException异常处理......");
        }
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

    // 4. 分页查询，有条件
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageListTeacherCondition(@PathVariable("current") long current,
                                      @PathVariable("limit") long limit,
                                      @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    // 5. 添加讲师接口
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save == true) {
            return R.ok();
        }
        return R.error();
    }

    // 6. 根据id查询讲师
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("info", teacher);
    }

    // 7. 修改讲师信息
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean updateById = eduTeacherService.updateById(eduTeacher);
        if (updateById == true) {
            return R.ok();
        }
        return R.error();
    }


}

