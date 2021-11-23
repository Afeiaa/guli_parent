package com.atguigu.staservice.schedule;


import com.atguigu.staservice.service.StaDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StaScheduleTask {

    @Autowired
    private StaDailyService staDailyService;


    @Scheduled(cron = "0/20 * * * * ? ")
    public void staRegisterCountSync() {
        staDailyService.saveRegisterCount("2021-11-23");
        System.out.println("staRegisterCountSync执行了............");
    }

}
