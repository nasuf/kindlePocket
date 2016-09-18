package com.kindlepocket.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/8/22.
 */
@Component
public class ScheduleTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private GridFSServiceImpl gridFSService;

    //@Scheduled(fixedRate = 5000)
    // execute every 10 seconds.
    @Scheduled(cron="0/10 * *  * * ? ")
    public void reportCurrentTime() {
        this.gridFSService.saveFiles();
    }

}
