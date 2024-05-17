package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * Custom scheduled task class
 */
@Component
@Slf4j
public class MyTask {

    /**
     * Scheduled tasks， trigger every 5 seconds
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
        log.info("Scheduled task starts execution：{}", new Date());
    }

}