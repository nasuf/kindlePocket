package com.kindlepocket.cms.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kindlepocket.cms.pojo.SearchResult;
import com.kindlepocket.cms.service.TextBookSearchService;

@Controller
@EnableAutoConfiguration
@RequestMapping("/search")
public class TextBookSearchController {

    private TextBookSearchService searchService = new TextBookSearchService();

    private static Logger logger = Logger.getLogger(TextBookSearchController.class);

    @RequestMapping("/title")
    public ModelAndView searchByKeyWords(@RequestParam("title") String title) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    public static void main(String[] args) {
        SpringApplication.run(TextBookSearchController.class, args);
    }

}
