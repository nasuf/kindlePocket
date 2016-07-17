package com.kindlepocket.web.service;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TextBookInfoSearchService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private ApiService apiService;

    private static Logger logger = Logger.getLogger(TextBookInfoSearchService.class);

    public JsonNode search(Map<String, Object> queryMap) {

      /*  List<String> titles = new ArrayList<String>();
        Set<String> keySet = queryMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        String key = null;
        while(iterator.hasNext()){
            String temp = iterator.next();
            if(temp.equals("key")){
                key = temp;
            }
        }
        if(logger.isInfoEnabled()){
            logger.info("query key is: "+ key);
        }*/
       /* Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("title", content);*/
        try {
            // String result = this.apiService.doGet("http://127.0.0.1:8081/search/title", contentMap);
            String result = this.apiService.doGet("http://127.0.0.1:9091/bookManage/findAll", queryMap);
            JsonNode readTree = MAPPER.readTree(result);
            System.out.println("readTree: " + readTree);
            return readTree;
           /* Iterator<JsonNode> iterator = readTree.iterator();
            while (iterator.hasNext()) {
                titles.add(iterator.next().get("title").toString());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
