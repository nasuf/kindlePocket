package com.kindlepocket.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TextBookInfoSearchService {

    public List<String> search(String content) {

        List<String> titles = new ArrayList<String>();
        // test
        titles.add("张学良口述历史");
        titles.add("布谷鸟的呼唤");
        return titles;

    }

}
