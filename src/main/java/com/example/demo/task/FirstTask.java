package com.example.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FirstTask {


        @Scheduled(cron = "0 0 0 * * ?")
        public void first(){
            System.out.println("定时任务");

        }
}
