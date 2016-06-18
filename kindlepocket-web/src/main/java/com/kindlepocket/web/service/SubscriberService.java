package com.kindlepocket.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.web.pojo.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by admin on 2016/6/18.
 */
@Component
public class SubscriberService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String ADD_SUBSCRIBER_URL = "http://localhost:8081/subscriber/add";
    private static final String IS_BINDED_SUBSCRIBER_URL = "http://localhost:8081/subscriber/findIsBinded";

    private static Logger logger = Logger.getLogger(SubscriberService.class);

    @Autowired
    private ApiService apiService;

    public void binding(String subsriberOpenId,String phone, String email, String emailPwd, String kindleEmail){

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("subscriberOpenId",subsriberOpenId);
        map.put("phone",phone);
        map.put("email",email);
        map.put("emailPwd",emailPwd);
        map.put("kindleEmail",kindleEmail);

        try {
            this.apiService.doPost(ADD_SUBSCRIBER_URL,map);
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("add new subscriber failed!");
            }
        }
    }

    public Boolean findIsBinded(String subscriberOpenId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("subscriberOpenId",subscriberOpenId);
        try {
            HttpResult result = this.apiService.doPost(IS_BINDED_SUBSCRIBER_URL, map);
            String body = result.getBody();
            Integer code = result.getCode();
            System.out.println("body:"+body+" code:"+code);
            if(!StringUtils.isEmpty(body)){
                if(logger.isInfoEnabled()){
                    logger.info("the user has binded");
                }
                return true;
            } else {
                if(logger.isInfoEnabled()){
                    logger.info("the user has not binded");
                }
                return false;
            }
        } catch (Exception e) {
            if(logger.isWarnEnabled()){
                logger.warn("finding if is binded process occured some problems");
            }
        }
        return false;
    }

}
