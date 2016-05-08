package com.kindlepocket.cms.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.SearchResult;
import com.kindlepocket.cms.service.TextBookSearchService;

@RestController
@RequestMapping("/search")
public class TextBookSearchController {

    @Autowired
    private TextBookSearchService searchService;// = new TextBookSearchService();

    private static Logger logger = Logger.getLogger(TextBookSearchController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping("/title")
    public ResponseEntity<String> searchByKeyWords(@RequestParam("title") String title) {

        if (logger.isInfoEnabled()) {
            logger.info("***search key words of title:" + title);
        }

        ModelAndView mv = new ModelAndView();
        try {
            SearchResult result = this.searchService.search(title, 1, 5);
            mv.addObject("searchResult", result);
            if (logger.isInfoEnabled()) {
                logger.info("search result:" + result.toString());
            }
            return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public static void main(String[] args) {
        SpringApplication.run(TextBookSearchController.class, args);
    }

}
