package com.kindlepocket.cms.service;

import com.kindlepocket.cms.pojo.DeliveryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/9/4.
 */
@Component
public interface DeliveryRecordRepository extends MongoRepository<DeliveryRecord, String> {
}
