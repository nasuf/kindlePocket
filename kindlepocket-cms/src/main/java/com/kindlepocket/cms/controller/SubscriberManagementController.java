package com.kindlepocket.cms.controller;

import com.kindlepocket.cms.pojo.Subscriber;
import com.kindlepocket.cms.service.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by admin on 2016/6/18.
 */

@RestController
@RequestMapping("/subscriber")
public class SubscriberManagementController {

    @Autowired
    SubscriberRepository ssbRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Subscriber> add(@RequestParam("subscriberOpenId") String subscriberOpenId, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("emailPwd") String emailPwd, @RequestParam("kindleEmail") String kindleEmail) {

        Subscriber ssb = new Subscriber();
        ssb.setId(subscriberOpenId);
        ssb.setEmail(email);
        ssb.setEmailPwd(emailPwd);
        ssb.setKindleEmail(kindleEmail);
        ssb.setSubscribeDate(new Date());

        Subscriber newSsb = this.ssbRepository.insert(ssb);

        return null;
    }

}
