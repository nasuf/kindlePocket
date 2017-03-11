package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kindlepocket.cms.pojo.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findBySubscriberOpenId(String subscriberOpenId);
	
}
