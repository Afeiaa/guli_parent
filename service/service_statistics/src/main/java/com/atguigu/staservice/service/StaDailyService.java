package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StaDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface StaDailyService extends IService<StaDaily> {
    boolean saveRegisterCount(String day);

    Map<String, Object> getChartData(String type, String start, String end);
}
