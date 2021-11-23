package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.staservice.entity.StaDaily;
import com.atguigu.staservice.service.StaDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/staservice/statistics")
public class StaDailyController {

    @Autowired
    private StaDailyService staDailyService;

    // 1. 存入数据库
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        boolean result = staDailyService.saveRegisterCount(day);
        if(result == false) {
            throw new GuliException(20001, "统计错误！");
        }
        return R.ok();
    }

    // 2. 根据时间范围，类型，查询
    @PostMapping("/getChartData/{type}/{start}/{end}")
    public R getChartData(@PathVariable("type") String type,
                          @PathVariable("start") String start,
                          @PathVariable("end") String end) {
        Map<String, Object> map =staDailyService.getChartData(type, start, end);
        return R.ok().data(map);
    }



}
