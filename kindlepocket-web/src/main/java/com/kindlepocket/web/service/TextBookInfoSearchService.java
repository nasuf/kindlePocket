package com.kindlepocket.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextBookInfoSearchService {

    @Autowired
    private ApiService apiService;

    public List<String> search(String content) {

        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("title", content);
        try {
            String result = this.apiService.doGet("http://127.0.0.1:8081/search/title", contentMap);
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> titles = new ArrayList<String>();
        // test
        titles.add("张学良口述历史");
        titles.add("布谷鸟的呼唤");
        return titles;

    }

}
