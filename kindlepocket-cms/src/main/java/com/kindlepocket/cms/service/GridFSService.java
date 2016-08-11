package com.kindlepocket.cms.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kindlepocket.cms.utils.Constants;
import com.kindlepocket.cms.utils.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class GridFSService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    private BookRepository bookRepository;

    private static Logger logger = Logger.getLogger(GridFSService.class);

    public void saveFiles() {

        DB db = mongoOperations.getCollection(mongoOperations.getCollectionName(TextBook.class)).getDB();
        db.requestStart();
        List<File> files = new ArrayList<File>();
        files = FileUtil.listFiles(Constants.UPLOAD_PATH_LOCAL, files);

        for(File uploadedFile: files){
            String uploadedFileName = uploadedFile.getName();
            GridFSInputFile gfsInput;
            try {
                gfsInput = new GridFS(db, "fs").createFile(uploadedFile);
                gfsInput.setId(new ObjectId(new Date()));
                // set gridFs chunckSize as 10M
                gfsInput.setChunkSize(1024 * 1024 * 10L);
                // set extension name
                gfsInput.setContentType("txt");
                // set file name saved in gridfs
                gfsInput.setFilename(uploadedFile.getName());
                gfsInput.save();
                if(logger.isInfoEnabled()){
                    logger.info("save book [" + uploadedFileName + "] to gridFS");
                }

                TextBook book = new TextBook();
                book.setId(gfsInput.getId().toString());
                book.setTitle(uploadedFileName);
                book.setAuthor(null);
                book.setUploadDate(new Date().getTime());
                book.setUploaderName(null);
                book.setMailTimes(0L);
                book.setKindleMailTimes(0L);
                book.setDownloadTimes(0L);
                book.setSize(gfsInput.getLength());
                book.setFormat(gfsInput.getContentType());
                bookRepository.save(book);
                if(logger.isInfoEnabled()){
                    logger.info("save book [" + uploadedFileName + "] to collection");
                }
            } catch (IOException e) {
                if(logger.isErrorEnabled()){
                    logger.error("save file to gridFS failed!", e);
                }
            }
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
