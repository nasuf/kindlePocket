package com.kindlepocket.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.Subscriber;
import com.kindlepocket.cms.service.SubscriberRepository;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by admin on 2016/6/18.
 */

@RestController
@RequestMapping("/subscriber")
public class SubscriberManagementController {

    @Autowired
    SubscriberRepository ssbRepository;

    private static Logger logger = Logger.getLogger(SubscriberManagementController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestParam("subscriberOpenId") String subscriberOpenId, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("emailPwd") String emailPwd, @RequestParam("kindleEmail") String kindleEmail) {

        Subscriber ssb = new Subscriber();
        ssb.setId(subscriberOpenId);
        ssb.setPhone(phone);
        ssb.setEmail(email);
        ssb.setEmailPwd(emailPwd);
        ssb.setKindleEmail(kindleEmail);
        ssb.setSubscribeDate(new Date());

        Subscriber newSsb = this.ssbRepository.insert(ssb);
        if(null == newSsb){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(ssb));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/findIsBinded", method = RequestMethod.POST)
    public ResponseEntity<String> findIsBinded(@RequestParam(value = "subscriberOpenId")String subscriberOpenId) {
        if (logger.isInfoEnabled()) {
            logger.info("finding if is binded:" + subscriberOpenId);
        }
        Subscriber subscriber = this.ssbRepository.findOne(subscriberOpenId);
        System.out.println(subscriber);

        if(null != subscriber){
            return ResponseEntity.status(HttpStatus.OK).body("true");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

}
