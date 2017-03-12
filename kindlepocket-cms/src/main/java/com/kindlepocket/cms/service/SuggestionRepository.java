package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kindlepocket.cms.pojo.Suggestion;

public interface SuggestionRepository extends MongoRepository<Suggestion,String>{
	
	List<Suggestion> findBySubscriberOpenId(String subscriberOpenId);
	
}
