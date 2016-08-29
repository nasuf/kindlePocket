package com.kindlepocket.cms.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Pack200;

import com.kindlepocket.cms.utils.Constants;
import com.kindlepocket.cms.utils.DateFormatUtils;
import com.kindlepocket.cms.utils.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.bcel.classfile.Constant;
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
        files = FileUtil.listFiles(Constants.UN_SAVED_PATH, files);
        if(files.size() != 0){
            if(logger.isInfoEnabled()){
                logger.info(files.size() + " books ready to save.");
            }
            for(File scannedFile: files){
                String originalFileName = scannedFile.getName().substring(
                        scannedFile.getName().indexOf(Constants.UNDER_LINE)+1,
                        scannedFile.getName().length());
                GridFSInputFile gfsInput;
                try {
                    gfsInput = new GridFS(db, "fs").createFile(scannedFile);
                    gfsInput.setId(new ObjectId(new Date()));
                    // set gridFs chunckSize as 10M
                    gfsInput.setChunkSize(1024 * 1024 * 10L);
                    // set extension name
                    gfsInput.setContentType("txt");
                    // set file name saved in gridfs
                    gfsInput.setFilename(originalFileName);
                    gfsInput.save();
                    if(logger.isInfoEnabled()){
                        logger.info("save book [" + originalFileName + "] to gridFS");
                    }

                    TextBook book = new TextBook();
                    book.setId(gfsInput.getId().toString());
                    book.setTitle(originalFileName);
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
                        logger.info("save book [" + originalFileName + "] to collection");
                    }

                    // eg: /usr/local/src/files/unsaved/20160815/*
                    String unSavedFilePath = scannedFile.getPath();
                    if(unSavedFilePath.contains(Constants.SPACE)){
                        unSavedFilePath.replace(Constants.SPACE, Constants.BACKSLASH+Constants.SPACE);
                    }
                    File savedFile = new File(Constants.SAVED_PATH + DateFormatUtils.parseDateToString(new Date().getTime()));
                    if(!savedFile.exists()) {
                        if(logger.isInfoEnabled()){
                            logger.info("make dir: " + savedFile.getPath());
                        }
                        savedFile.mkdirs();
                    }
                    /*String command = "mv " + unSavedFilePath + " " + savedFile.getPath();
                    if(logger.isInfoEnabled()){
                        logger.info("prepared to execute command: " + command);
                    }
                    // execute linux command to move scanned file to the destination directory.
                    Process process = Runtime.getRuntime().exec(command);*/
                    FileUtil.moveFile(unSavedFilePath,savedFile.getPath());

                    if(logger.isInfoEnabled()){
                        logger.info("file [" + scannedFile.getName() + "] has been moved to the destination directory");
                    }
                } catch (IOException e) {
                    if(logger.isErrorEnabled()){
                        logger.error("save file to gridFS failed!", e);
                    }
                }
                db.requestDone();
            }
        } /*else {
            if(logger.isInfoEnabled()){
                logger.info("no files scanned!");
            }
        }*/
    }

/*    public static void main(String args[]){
        File file = new File("/src/main/resources/static/files/unsaved/20160813/2016081318082057_HttpClientUtil.java");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParentFile());
        try {
            FileUtils.copyFile(file,new File("/src/main/resources/static/files/saved/1.jpg"));
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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
