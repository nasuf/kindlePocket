package com.kindlepocket.cms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kindlepocket.cms.pojo.Subscriber;
import com.kindlepocket.cms.service.SubscriberRepository;
import com.kindlepocket.cms.utils.Constants;
import com.kindlepocket.cms.utils.DateFormatUtils;
import com.kindlepocket.cms.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.TextBook;
import com.kindlepocket.cms.service.BookRepository;
import com.kindlepocket.cms.service.GridFSService;
import com.kindlepocket.cms.service.MailService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/bookManage")
public class BookManagementController {

    private static Logger logger = Logger.getLogger(BookManagementController.class);



    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SubscriberRepository ssbRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private GridFSService gridFSService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping("/sendMail")
    public void sendMail(@RequestParam("bookId")String bookId, @RequestParam("subscriberOpenId")String subscriberOpenId){

        Subscriber s = this.ssbRepository.findOne(subscriberOpenId);
        String fromMail = s.getEmail();
        String toMail = s.getKindleEmail();
        String fromMailPwd = s.getEmailPwd();
        if(logger.isInfoEnabled()){
            logger.info("prepared to send email for " + s.getUserName() + " from : [" + fromMail + "] to : [" + toMail + "]");
        }
        this.mailService.sendFileAttachedMail(fromMail,toMail,fromMailPwd,bookId);
        if(logger.isInfoEnabled()){
            logger.info("mail send successfully!");
        }
    }

    @RequestMapping("/insert")
    public void testInsert() {
        System.out.println("inserting............");
        List<TextBook> books = new ArrayList<TextBook>();
        for (int i = 0; i < 500; i++) {
            TextBook book = new TextBook();
            book.setId(i+"");
            book.setAuthor("nasuf_" + i);
            book.setTitle("ephemeris_No." + i);
            book.setUploadDate(new Date().getTime());
            book.setDownloadTimes(0L);
            book.setKindleMailTimes(0L);
            book.setUploaderName("nasuf");
            book.setSize(0L);
            book.setFormat("mobi");
            books.add(book);
        }
        this.bookRepository.insert(books);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findAll(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        List<TextBook> books = new ArrayList<TextBook>();
        if(logger.isInfoEnabled()){
            logger.info("query key: " + key + " query value: " + value);
        }
        if(key.toString().equals("title")){
            books = this.bookRepository.findByTitleLike(value);
            if(logger.isInfoEnabled()){
                logger.info("query results: " + books.toString());
            }
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(books));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if(key.toString().equals("id")){
            TextBook book = this.bookRepository.findById(Long.parseLong(value));
            books.add(book);
            if(logger.isInfoEnabled()){
                logger.info("query result: " + books.toString());
            }
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(books));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.GET)
    public void saveBooks() {

        if (logger.isInfoEnabled()) {
            logger.info("\n saving book ");
        }
        Long startTime = new Date().getTime();
        this.gridFSService.saveFiles();
        Long endTime = new Date().getTime();
        if (logger.isInfoEnabled()) {
            logger.info("all books have been saved successfully! time cost "
                    + (endTime - startTime) / 1000 + " seconds");
        }

    }

    @RequestMapping("/revome")
    public void testRemove() {
        System.out.println("removing...");
        this.bookRepository.deleteAll();
        System.out.println("removment finished!");
    }

    @RequestMapping("/testFind")
    public void testFind(){
        System.out.println(this.bookRepository.findByTitleLike("ephemeris"));
        System.out.println(this.bookRepository.findById(100L));
    }

    @RequestMapping("/toUpload")
    public String toUpload(){
        if(logger.isInfoEnabled()){
            logger.info("redirecting to upload page...");
        }
        return "upload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String uploadFile(/*@RequestParam("file") CommonsMultipartFile file,
                             @RequestParam("uploaderName") String uploaderName,*/
                             HttpServletRequest request,
                             HttpServletResponse response) {

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        for (int i =0; i< files.size(); ++i) {
            MultipartFile file = files.get(i);
            File uploadedFile = null;
           // this.gridFSService.saveFiles(file);

            String fileOriginalName = file.getOriginalFilename();
            String uploadFileName = DateFormatUtils.parseDateTimeToString(new Date().getTime())+ Constants.UNDER_LINE+fileOriginalName;
            if (!file.isEmpty()) {
                try {
                    File parentFilePath = new File(Constants.UPLOAD_PATH_LOCAL+DateFormatUtils.parseDateToString(new Date().getTime()));
                    if(!parentFilePath.exists()){
                        parentFilePath.mkdirs();
                    }
                    byte[] bytes = file.getBytes();
                    uploadedFile = new File(parentFilePath+Constants.SLASH+uploadFileName);
                    uploadedFile.setWritable(true,true);
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if(logger.isErrorEnabled()){
                        logger.error("file upload failed!", e);
                    }
                }
            } else {
                if(logger.isWarnEnabled()){
                    logger.warn("no file ready to upload!");
                }
            }
        }
        return "upload successful";


     /*   if(logger.isInfoEnabled()){
            logger.info("ready to upload files...");
        }
        String parentPathStr = null;
        String filePath = null;
        String fileName = file.getOriginalFilename();
        String fileType = null;
        String fileNameUploaded = null;
        String currentTimeStr = DateFormatUtils.parseDateToStringUnsplited(System.currentTimeMillis());
        // 为了避免文件名重复，在文件名前加UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // String uuidFileName = uuid + fileName;

        fileNameUploaded = uuid + "-uploadedIn-" + currentTimeStr + "-uploadedBy-" + uploaderName + "-named-"
                + fileName;
       *//* if (true == toCommonRepo) {
            parentPathStr = request.getSession().getServletContext()
                    .getRealPath("/files");
            if (logger.isInfoEnabled()) {
                logger.info(uploaderName + "正在上传该文件: " + fileName + " 到公共库");
            }
        } else {*//*
            // 设置文件保存的本地路径
            filePath = request.getSession().getServletContext().getRealPath("/files");
            // fileName = file.getOriginalFilename();
            fileType = fileName.split("[.]")[1];

            File parentPath = new File(filePath + "/" + uploaderName);
            if (!parentPath.exists())
                parentPath.mkdirs();
            parentPathStr = request.getSession().getServletContext()
                    .getRealPath("/files/" + uploaderName + "");
            File f = new File(parentPath + "/" + uuid + "." + fileType);
            if (logger.isInfoEnabled()) {
                logger.info(uploaderName + "正在上传该文件: " + fileName + " 到私人库");
            }
        //}

        // 将文件保存到服务器
        try {
            FileUtil.upFile(file.getInputStream(), fileNameUploaded, parentPathStr);
        } catch (IOException e) {
            if(logger.isErrorEnabled()){
                logger.error("file upload failed!", e);
            }
        }
        return fileNameUploaded;*/
    }
}
