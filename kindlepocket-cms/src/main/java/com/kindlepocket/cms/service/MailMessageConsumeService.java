package com.kindlepocket.cms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.RabbitMQConfig;
import com.kindlepocket.cms.pojo.DeliveryRecord;
import com.kindlepocket.cms.pojo.Subscriber;
import com.kindlepocket.cms.pojo.TextBook;
import com.kindlepocket.cms.utils.Constants;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

/**
 * Created by admin on 2016/7/30.
 */
@Component
public class MailMessageConsumeService {

    private static Logger logger = Logger.getLogger(MailMessageConsumeService.class);

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    SubscriberRepository ssbRepository;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DeliveryRecordRepository deliveryRecordRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String EXCHANGE   = "kindlePocket-mail-exchange";
    public static final String ROUTINGKEY = "kindlePocket-mail-routingKey";
    public static final String QUEUE = "kindlePocket-mail-queue";

    @Bean
    public Queue queue() {
        //queue persistent
        return new Queue(QUEUE,true);
    }

    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(defaultExchange())
                .with(ROUTINGKEY);
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(rabbitMQConfig.connectionFactory());
        listenerContainer.setQueues(queue());
        listenerContainer.setExposeListenerChannel(true);
        // default value is 1
        listenerContainer.setMaxConcurrentConsumers(1);
        // set manual acknowledgeMode
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listenerContainer.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, Channel channel) throws Exception {

                byte[] body = message.getBody();
                String msg = new String(body);
                if(logger.isInfoEnabled()){
                    logger.info("get message from queue: " + msg);
                }

                try {
                    JsonNode jsonNode = MAPPER.readTree(msg);
                    //String bookId = jsonNode.get("bookId").toString();
                    String bookId = jsonNode.get("bookId").asText();
                    //String subscriberOpenId = jsonNode.get("subscriberOpenId").toString();
                    String subscriberOpenId = jsonNode.get("subscriberOpenId").asText();
                    sendMail(bookId, subscriberOpenId);
                } catch (IOException e) {
                    if(logger.isErrorEnabled()){
                        logger.error("parse msg error!",e);
                    }
                }

                // confirm message consumed successfully
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        });
        return listenerContainer;
    }

    public void sendMail(String bookId, String subscriberOpenId){

        Subscriber s = this.ssbRepository.findOne(subscriberOpenId);
        String fromMail = s.getEmail();
        String toMail = s.getKindleEmail();
        String fromMailPwd = s.getEmailPwd();
        if(logger.isInfoEnabled()){
            logger.info("prepared to send email for " + s.getUserName() + " from : [" + fromMail + "] to : [" + toMail + "]");
        }
        this.mailService.sendFileAttachedMail(fromMail,toMail,fromMailPwd,bookId);
        TextBook book = this.bookRepository.findOne(bookId);
        // update mail times
        book.setKindleMailTimes(book.getKindleMailTimes() + Constants.ONE);
        this.bookRepository.save(book);

        // save delivery record;
        DeliveryRecord record = new DeliveryRecord();
        record.setSubscriberOpenId(subscriberOpenId);
        record.setDeliveryDate(new Date());
        record.setFromEmailAdd(fromMail);
        record.setToEmailAdd(toMail);
        record.setIsDelivered(Constants.ONE);
        record.setTextBookId(bookId);
        this.deliveryRecordRepository.save(record);

        if(logger.isInfoEnabled()){
            logger.info("save new delivery record: [" + record.toString() + "]");
            logger.info("mail send successfully!");
        }
    }

}
