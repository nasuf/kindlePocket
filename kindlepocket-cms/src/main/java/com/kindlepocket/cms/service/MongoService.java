package com.kindlepocket.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/6/24.
 */
@Component
public class MongoService {

    @Autowired
    MongoOperations mongoOperations;

    Query query;

    public <T> T update(String key, String value, Class<T> entityClass, String updateKey, String updateValue){
        return null;
    }

}
