package com.kindlepocket.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kindlepocket.web.service.TextBookInfoSearchService;
import com.kindlepocket.web.util.CheckUtil;
import com.kindlepocket.web.util.MessageUtil;

@Controller
@RequestMapping("/Weixin")
public class KindlePocketController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private TextBookInfoSearchService searchService;// = new TextBookInfoSearchService();

    private static Logger logger = Logger.getLogger(KindlePocketController.class);

    @RequestMapping("/homepage")
    public String toIndex() {
        System.out.println("redirecting to homepage...");
        return "index";
    }

    @RequestMapping(value = "/wx.do", method = RequestMethod.GET)
    public void validate(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {

        if (logger.isInfoEnabled()) {
            logger.info("\n***The message got: signature:" + signature + " timestamp:" + timestamp
                    + " nonce:" + nonce + " echostr:" + echostr);
        }

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("response error");
            }
        }

        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
            if (logger.isInfoEnabled()) {
                logger.info("validated!");
            }
        }
    }

    @RequestMapping(value = "/wx.do", method = RequestMethod.POST)
    public ResponseEntity processMessage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        /*
         * PrintWriter out = null; try { out = response.getWriter(); } catch (IOException e) { e.printStackTrace(); }
         */

        try {

            Map<String, String> map = MessageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            if (logger.isInfoEnabled()) {
                logger.info("\n***The message got: fromUserName:" + fromUserName + " toUserName:"
                        + toUserName + " msgType:" + msgType + " content:" + content);
            }

            String responseMessage = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {

                switch (content) {
                case "1":
                    responseMessage = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                    break;
                case "2":
                    responseMessage = MessageUtil
                            .initText(toUserName, fromUserName, MessageUtil.secondMenu());
                    break;
                case "3":
                    responseMessage = MessageUtil.initPicTextMessage(toUserName, fromUserName);
                    break;
                default:
                    // responseMessage = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                    List<String> titleList = this.searchService.search(content);
                    logger.info("找到" + titleList.size() + "本书籍");
                    responseMessage = MessageUtil.initPicTextMessage(toUserName, fromUserName, titleList);
                    break;
                }

                /*
                 * TextMessage textMessage = new TextMessage(); textMessage.setFromUserName(toUserName); textMessage.setToUserName(fromUserName); textMessage.setMsgType("text"); textMessage.setCreateTime(new Date().getTime()); textMessage.setContent("the message you sent was : " + content); responseMessage = MessageUtil.textMessageToXml(textMessage); if (logger.isInfoEnabled()) { logger.info("the message responsed is :\n" + responseMessage); }
                 */
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    responseMessage = MessageUtil.initText(toUserName, fromUserName,
                            MessageUtil.welcomeText());
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("\n***The message responsed: \n" + responseMessage);
            }
            // out.print(responseMessage);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } /*
           * finally { out.close(); }
           */

    }

    /*
     * public static void main(String[] args) { SpringApplication.run(KindlePocketController.class, args); }
     */

}
