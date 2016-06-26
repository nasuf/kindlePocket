package com.kindlepocket.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.kindlepocket.web.pojo.HttpResult;
import com.kindlepocket.web.pojo.Subscriber;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by admin on 2016/6/18.
 */
@Component
public class SubscriberService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String CMS_SUBSCRIBER_URL = "http://localhost:9091/subscriber";

    private static Logger logger = Logger.getLogger(SubscriberService.class);

    @Autowired
    private ApiService apiService;

    public void binding(String subsriberOpenId,String phone, String userName, String email, String emailPwd, String kindleEmail){

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("subscriberOpenId",subsriberOpenId);
        map.put("userName", userName);
        map.put("phone",phone);
        map.put("email",email);
        map.put("emailPwd",emailPwd);
        map.put("kindleEmail",kindleEmail);

        try {
            this.apiService.doPost(CMS_SUBSCRIBER_URL+"/add",map);
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("add new subscriber failed!");
            }
        }
    }

    public HttpResult findSubscriberInfo(String subscriberOpenId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("subscriberOpenId",subscriberOpenId);
        try {
            HttpResult result = this.apiService.doPost(CMS_SUBSCRIBER_URL+"/findSubscriberInfo", map);
            String body = result.getBody();
            Integer code = result.getCode();
            System.out.println("body:"+body+" code:"+code);
            return result;
        } catch (Exception e) {
            if(logger.isWarnEnabled()){
                logger.warn("finding if is binded process occured some problems");
            }
        }
        return null;
    }

    public Boolean updateSubscriberInfo(String id, String phone, String userName, String email, String emailPwd, String kindleEmail) {
        Subscriber subscriber = new Subscriber(id, userName, phone, email, new Date(), kindleEmail, emailPwd);
        try {
            String ssb = MAPPER.writeValueAsString(subscriber);
            System.out.println("ssb:" + ssb);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("subscriber", ssb);
            HttpResult result = this.apiService.doPost(CMS_SUBSCRIBER_URL + "/updateSubscriberInfo", map);
            if(result.getCode().equals(200)){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
