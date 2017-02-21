package com.kindlepocket.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.DeliveryRecord;
import com.kindlepocket.cms.pojo.Subscriber;
import com.kindlepocket.cms.service.DeliveryRecordRepository;
import com.kindlepocket.cms.service.SubscriberRepository;
import com.kindlepocket.cms.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/6/18.
 */

@RestController
@RequestMapping("/subscriber")
public class SubscriberManagementController {

    @Autowired
    SubscriberRepository ssbRepository;

    @Autowired
    DeliveryRecordRepository deliveryRecordRepository;

    private static Logger logger = Logger.getLogger(SubscriberManagementController.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestParam("subscriberOpenId")String subscriberOpenId){
        Subscriber temp = this.ssbRepository.findOne(subscriberOpenId);
        Subscriber newSsb = null;
        if(null == temp){
            // the user is never subscribed.
            if(logger.isInfoEnabled()){
                logger.info("the user is never subscribed before, then add it.");
            }
            Subscriber ssb = new Subscriber();
            ssb.setIsBinded(0);
            ssb.setId(subscriberOpenId);
            ssb.setSubscribeDate(new Date());
            ssb.setLastChangeDate(new Date());
            ssb.setIsActive(1);
            newSsb = this.ssbRepository.insert(ssb);
            if(logger.isInfoEnabled()){
                logger.info("reActive the subscriber successfully!");
            }
        } else {
            // the user is ever subscribed previously.
            temp.setIsActive(1);
            temp.setSubscribeDate(new Date());
            temp.setLastChangeDate(new Date());
            this.ssbRepository.save(temp);
            if(logger.isInfoEnabled()){
                logger.info("add the subscriber successfully!");
            }
            newSsb = temp;
        }

        if(null == newSsb){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(newSsb));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public void setInActive(@RequestParam("subscriberOpenId") String subscriberOpenId){
        Subscriber temp = this.ssbRepository.findOne(subscriberOpenId);
        temp.setIsActive(Constants.ZERO);
        temp.setLastChangeDate(new Date());
        this.ssbRepository.save(temp);
        if(logger.isInfoEnabled()){
            logger.info("subscriber:" + subscriberOpenId + " has unsubscribed! Info: " + temp.toString());
        }
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResponseEntity<String> bindInfo(@RequestParam("subscriberOpenId") String subscriberOpenId, @RequestParam("phone") String phone, @RequestParam("userName")String userName, @RequestParam("email") String email, @RequestParam("emailPwd") String emailPwd, @RequestParam("kindleEmail") String kindleEmail) {

        Subscriber ssb = this.ssbRepository.findOne(subscriberOpenId);
        Subscriber newSsb = null;
        if(null == ssb) {
        	 Subscriber s = new Subscriber();
             s.setIsBinded(0);
             s.setId(subscriberOpenId);
             s.setSubscribeDate(new Date());
             s.setIsActive(1);
             s.setPhone(phone);
             s.setUserName(userName);
             s.setEmail(email);
             s.setEmailPwd(emailPwd);
             s.setKindleEmail(kindleEmail);
             s.setIsBinded(Constants.ONE);
             s.setLastChangeDate(new Date());
             newSsb = this.ssbRepository.insert(s);
        } else {
        	  ssb.setPhone(phone);
              ssb.setUserName(userName);
              ssb.setEmail(email);
              ssb.setEmailPwd(emailPwd);
              ssb.setKindleEmail(kindleEmail);
              ssb.setIsBinded(Constants.ONE);
              ssb.setLastChangeDate(new Date());
              newSsb = this.ssbRepository.save(ssb);
        }
      

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

    @RequestMapping(value = "/findSubscriberInfo", method = RequestMethod.POST)
    public ResponseEntity<String> findIsBinded(@RequestParam(value = "subscriberOpenId")String subscriberOpenId) {
        if (logger.isInfoEnabled()) {
            logger.info("finding if is binded:" + subscriberOpenId);
        }
        Subscriber subscriber = this.ssbRepository.findOne(subscriberOpenId);
        System.out.println(subscriber);
        if(null != subscriber){
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(subscriber));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @RequestMapping(value = "/findDeliveryRecords", method = RequestMethod.POST)
    public ResponseEntity<String> findDeliveryRecords(@RequestParam(value = "subscriberOpenId")String subscriberOpenId) {
        if (logger.isInfoEnabled()) {
            logger.info("finding delivery recored for: [" + subscriberOpenId + "]");
        }
        List<DeliveryRecord> recordList = this.deliveryRecordRepository.findBySubscriberOpenId(subscriberOpenId);
        if(logger.isInfoEnabled()) {
            logger.info("dilivery record list: [" + recordList.toString() + "]");
        }
        if(null != recordList){
            try {
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(recordList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @RequestMapping(value = "/updateSubscriberInfo", method = RequestMethod.POST)
    public ResponseEntity<String> updateSubscriberInfo(@RequestParam("subscriber")String subscriber){
        try {
            /*JsonNode jsonNode = MAPPER.readTree(subscriber);
            String ssbStr = jsonNode.get("Subscriber").toString();
            System.out.println("ssbStr:" + ssbStr);*/
            if(logger.isInfoEnabled()){
                logger.info("string got:" + subscriber);
            }
            Subscriber ssb = MAPPER.readValue(subscriber, Subscriber.class);
            ssb.setLastChangeDate(new Date());
            ssb.setIsBinded(Constants.ONE);
            ssb.setIsActive(Constants.ONE);
            ssb.setSubscribeDate(this.ssbRepository.findOne(ssb.getId()).getSubscribeDate());
            if(logger.isInfoEnabled()){
                logger.info("ssb:" + ssb);
            }
            Subscriber newSsbInfo = this.ssbRepository.save(ssb);
            return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(newSsbInfo));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
