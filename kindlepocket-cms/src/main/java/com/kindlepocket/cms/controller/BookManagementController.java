package com.kindlepocket.cms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.TextBook;
import com.kindlepocket.cms.service.BookRepository;
import com.kindlepocket.cms.service.GridFSService;
import com.kindlepocket.cms.service.MailService;

@RestController
@RequestMapping("/bookManage")
public class BookManagementController {

    private static Logger logger = Logger.getLogger(BookManagementController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private GridFSService gridFSService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping("/sendMail")
    public void sendMail(@RequestParam(value = "fileObjectId")String fileObjectId){
        this.mailService.sendFileAttachedMail(fileObjectId);
    }

    @RequestMapping("/insert")
    public void testInsert() {
        System.out.println("inserting............");
        List<TextBook> books = new ArrayList<TextBook>();
        for (int i = 0; i < 500; i++) {
            TextBook book = new TextBook();
            book.setId((long) i);
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
    public void saveBooks(@RequestParam(value = "title") String title) {

        if (logger.isInfoEnabled()) {
            logger.info("\n saving book named " + title);
        }
        Long startTime = new Date().getTime();
        this.gridFSService.saveFiles();
        Long endTime = new Date().getTime();
        if (logger.isInfoEnabled()) {
            logger.info("\n book " + title + " has been saved successfully! time cost "
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
}
