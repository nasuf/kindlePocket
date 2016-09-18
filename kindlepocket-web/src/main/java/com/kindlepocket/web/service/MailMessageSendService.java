package com.kindlepocket.web.service;

import com.kindlepocket.web.RabbitMQConfig;
import com.rabbitmq.client.AMQP;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by admin on 2016/7/30.
 */
@Component
public class MailMessageSendService implements RabbitTemplate.ConfirmCallback {

    private static Logger logger = Logger.getLogger(MailMessageSendService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

/*
    public MailMessageSendService (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }*/

    public void sendMsg(String content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.ROUTINGKEY,content,correlationId);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(logger.isInfoEnabled()){
            logger.info("callback id: " + correlationData);
        }
        if(ack) {
            if(logger.isInfoEnabled()){
                logger.info("message has been added in Queue successfully!");
            }
        } else {
            if(logger.isWarnEnabled()){
                logger.warn("message added to Queue failed!");
            }
        }
    }
}
