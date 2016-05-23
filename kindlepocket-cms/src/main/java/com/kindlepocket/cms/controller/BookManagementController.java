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

import com.kindlepocket.cms.pojo.Item;
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

    @RequestMapping("/insert")
    public void testInsert() {
        System.out.println("inserting............");
        List<Item> items = new ArrayList<Item>();
        for (int i = 400; i < 450; i++) {
            Item item = new Item();
            item.setId((long) i);
            item.setAuthor("nasuf_" + i);
            item.setTitle("ephemeris_No." + i);
            item.setUploadDate(new Date().getTime());
            items.add(item);
        }
        this.bookRepository.insert(items);
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public ResponseEntity<String> testFind(@RequestParam(value = "title") String title) {
        title = title.toString();
        System.out.println("title:" + title);
        Item book = this.bookRepository.findByTitle(title);
        System.out.println("book:" + book);
        this.mailService.sendMail(book.getTitle());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email Send Successfully!");
        // System.out.println(book.getAuthor());
        // List<Item> books = this.bookRepository.findAll();
        // for (Item item : books) {
        // System.out.println(item);
        // }
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.GET)
    public void saveBooks(@RequestParam(value = "title") String title) {

        if (logger.isInfoEnabled()) {
            logger.info("\n saving book named " + title);
        }
        Long startTime = new Date().getTime();
        this.gridFSService.saveFiles(title);
        Long endTime = new Date().getTime();
        if (logger.isInfoEnabled()) {
            logger.info("\n book " + title + "has been saved successfully! time cost "
                    + (endTime - startTime) / 1000 + " seconds");
        }

    }

    @RequestMapping("/revome")
    public void testRemove() {
        System.out.println("removing...");
        this.bookRepository.deleteAll();
        System.out.println("removment finished!");
    }

    @RequestMapping("/preview")
    public void previewBook() {
        this.gridFSService.readFiles();
    }
}
