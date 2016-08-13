package com.kindlepocket.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/8/12.
 */
@Component
public class FileProcess implements Runnable{

    @Autowired
    private GridFSService gridFSService;

    private final int threadId = 0;

    @Override
    public void run() {
        this.gridFSService.saveFiles();
    }
}
