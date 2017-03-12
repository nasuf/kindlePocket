package com.kindlepocket.web.service;

import java.io.IOException;
import java.util.*;

import com.kindlepocket.web.pojo.HttpResult;
import com.kindlepocket.web.pojo.Subscriber;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TextBookService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String CMS_BOOK_URL = "http://localhost:9091/bookManage";

    @Autowired
    private ApiService apiService;

    private static Logger logger = Logger.getLogger(TextBookService.class);

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
            String result = this.apiService.doGet(CMS_BOOK_URL + "/findAll", queryMap);
            JsonNode readTree = MAPPER.readTree(result);
            //System.out.println("readTree: " + readTree);
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

    public void sendMail(String bookId, String subscriberOpenId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bookId",bookId);
        map.put("subscriberOpenId",subscriberOpenId);
        try {
            this.apiService.doPost(CMS_BOOK_URL + "/sendMail", map);
        } catch (IOException e) {
            if(logger.isErrorEnabled()){
                logger.error("sendEmail request failed!",e);
            }
        }
    }
    
    public Boolean sendBookComment(String bookId, String subscriberOpenId, String content) {
    	Map<String, Object> map = new HashMap<String, Object> ();
    	map.put("bookId", bookId);
    	map.put("subscriberOpenId", subscriberOpenId);
    	map.put("content", content);
    	Boolean flag = false;
    	try {
			HttpResult result = this.apiService.doPost(CMS_BOOK_URL + "/sendBookComment", map);
			if (result.getCode().equals(200)) {
				flag = true;
			}
		} catch (IOException e) {
			if(logger.isErrorEnabled()) {
				logger.error("Send book comment failed! bookId: [ " + bookId + " ], subscriberOpenId: [ " + subscriberOpenId + " ], content: [ " + content + " ]", e);
			}
		}
    	return flag;
    }


}
