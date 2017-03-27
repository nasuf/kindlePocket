package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.Subscriber;

/**
 * Created by admin on 2016/6/18.
 */
@Component
public interface SubscriberRepository extends MongoRepository<Subscriber,String>{
	
	List<Subscriber> findByIsBinded(Integer isBinded);
	
	List<Subscriber> findByIsActive(Integer isActive);
	
}
