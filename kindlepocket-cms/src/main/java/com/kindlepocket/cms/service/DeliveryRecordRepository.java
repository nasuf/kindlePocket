package com.kindlepocket.cms.service;

import com.kindlepocket.cms.pojo.DeliveryRecord;
import com.rabbitmq.client.AMQP;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2016/9/4.
 */
@Component
public interface DeliveryRecordRepository extends MongoRepository<DeliveryRecord, String> {

    List<DeliveryRecord> findBySubscriberOpenId(String subscriberOpenId);

    DeliveryRecord findByTextBookId(ObjectId bookId);
}
