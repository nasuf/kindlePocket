package com.kindlepocket.cms.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.TextBook;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Component
public class GridFSService {

    @Autowired
    MongoOperations mongoOperations;

    public void saveFiles() {

        DB db = mongoOperations.getCollection(mongoOperations.getCollectionName(TextBook.class)).getDB();
        db.requestStart();
        //File uploadedFile = new File("src/main/resources/" + title + ".txt");
        File uploadedFile = new File("src/main/resources/application.properties");
        String uploadedFileName = uploadedFile.getName();
        GridFSInputFile gfsInput;
        try {
            gfsInput = new GridFS(db, "fs").createFile(uploadedFile);
            // gfsInput.setId(new ObjectId("1"));
            // set gridFs chunckSize as 10M
            gfsInput.setChunkSize(1024 * 1024 * 10L);
            // set extension name
            gfsInput.setContentType("txt");
            // set file name saved in gridfs
            gfsInput.setFilename(uploadedFile.getName());
            gfsInput.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.requestDone();
    }

    public File readFiles(String fileObjectId) {
        DB db = mongoOperations.getCollection(mongoOperations.getCollectionName(TextBook.class)).getDB();

        // query file saved in gridfs
        // by file name
        //GridFSDBFile gfsFile = new GridFS(db, "fs").findOne("application.properties");
        // by objectId
        GridFSDBFile gfsFile = new GridFS(db,"fs").findOne(new ObjectId(fileObjectId));
        try {
            File preparedAttachedFile = new File(gfsFile.getFilename());
           /* if (!preparedAttachedFile.exists()) {
                preparedAttachedFile.createNewFile();
            }*/
           // gfsFile.writeTo("src/main/resources/" + new Date().getTime() + "-" + uploadedFile.getName());
            gfsFile.writeTo(preparedAttachedFile);
            return preparedAttachedFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
