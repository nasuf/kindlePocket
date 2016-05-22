package com.kindlepocket.cms.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.Item;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Component
public class GridFSService {

    @Autowired
    MongoOperations mongoOperations;

    public void saveFiles(String title) {

        DB db = mongoOperations.getCollection(mongoOperations.getCollectionName(Item.class)).getDB();
        db.requestStart();
        File uploadedFile = new File("src/main/resources/" + title + ".txt");
        String uploadedFileName = uploadedFile.getName();
        GridFSInputFile gfsInput;
        try {
            gfsInput = new GridFS(db, "fs").createFile(uploadedFile);
            // get extension name
            gfsInput.setContentType("txt");
            gfsInput.setFilename(uploadedFile.getName());// 保存到数据库的文件名为qq123456789logo
            gfsInput.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.requestDone();
    }

    public void readFiles() {
        DB db = mongoOperations.getCollection(mongoOperations.getCollectionName(Item.class)).getDB();

        GridFSDBFile gfsFile = new GridFS(db, "fs").findOne("阿特拉斯耸耸肩.txt");// 查找文件名qq123456789logo输出保存
        try {
            File uploadedFile = new File(gfsFile.getFilename());
            if (!uploadedFile.exists()) {
                uploadedFile.createNewFile();
            }
            gfsFile.writeTo("src/main/resources/" + new Date().getTime() + "-" + uploadedFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
