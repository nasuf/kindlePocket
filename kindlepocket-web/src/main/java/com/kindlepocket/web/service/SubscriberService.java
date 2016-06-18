package com.kindlepocket.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by admin on 2016/6/18.
 */
@Component
public class SubscriberService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String ADD_SUBSCRIBER_URL = "http://localhost:8081/subscriber/add";

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

}
