package com.kindlepocket.cms.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.kindlepocket.cms.pojo.SearchRecord;
import com.kindlepocket.cms.pojo.TextBook;

@Component
public interface SearchRecordRepository extends MongoRepository<SearchRecord, String> {


}
