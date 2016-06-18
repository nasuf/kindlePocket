package com.kindlepocket.cms.service;

import com.kindlepocket.cms.pojo.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/6/18.
 */
@Component
public interface SubscriberRepository extends MongoRepository<Subscriber,String>{


}
