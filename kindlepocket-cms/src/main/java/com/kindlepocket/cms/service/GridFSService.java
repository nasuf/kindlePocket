package com.kindlepocket.cms.service;

import java.io.File;

/**
 * Created by nasuf on 16/9/17.
 */
public interface GridFSService {

    void saveFiles();

    File readFiles(String fileObjectId);



}
