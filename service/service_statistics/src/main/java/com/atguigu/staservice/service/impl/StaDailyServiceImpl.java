package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StaDaily;
import com.atguigu.staservice.mapper.StaDailyMapper;
import com.atguigu.staservice.service.StaDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaDailyServiceImpl extends ServiceImpl<StaDailyMapper, StaDaily> implements StaDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    // 1. 存入统计数据
    @Override
    public boolean saveRegisterCount(String day) {
        // 先删除
        QueryWrapper<StaDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        // 再存入
        Integer registerCount = ucenterClient.registerCountByDay(day);
        StaDaily staDaily = new StaDaily();
        staDaily.setRegisterNum(registerCount);
        staDaily.setDateCalculated(day);
        // 其他值使用数据数生成
        staDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        staDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        staDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        int result = baseMapper.insert(staDaily);
        return result > 0;
    }

    // 2. 查询统计数据
    @Override
    public Map<String, Object> getChartData(String type, String start, String end) {
        QueryWrapper<StaDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", start, end);
        wrapper.select("date_calculated", type);
        List<StaDaily> staDailies = baseMapper.selectList(wrapper);

        // 封装数据    x:日期   y:数据
        List<String> dateCalculated = new ArrayList<>();
        List<Integer> chartDta = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        // 遍历
        for (StaDaily staDaily : staDailies) {
            // x:日期
            dateCalculated.add(staDaily.getDateCalculated());
            // y:数据
            switch (type) {
                case "login_num":
                    chartDta.add(staDaily.getLoginNum());
                    break;
                case "register_num":
                    chartDta.add(staDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    chartDta.add(staDaily.getVideoViewNum());
                    break;
                case "course_num":
                    chartDta.add(staDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        map.put("dateCalculated", dateCalculated);
        map.put("chartDta", chartDta);
        return map;
    }


}
