package com.kindlepocket.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kindlepocket.web.pojo.PicText;
import com.kindlepocket.web.pojo.PicTextMessage;
import com.kindlepocket.web.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";

    public static final String MESSAGE_PIC_TEXT = "news";

    public static final String MESSAGE_IMAGE = "image";

    public static final String MESSAGE_VOICE = "voice";

    public static final String MESSAGE_VIDEO = "video";

    public static final String MESSAGE_EVENT = "event";

    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_CLICK = "CLICK";

    public static final String MESSAGE_VIEW = "VIEW";

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException,
            DocumentException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document document = reader.read(ins);

        Element root = document.getRootElement();
        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        ins.close();
        return map;

    }

    public static String textMessageToXml(TextMessage message) {

        XStream xStream = new XStream();
        xStream.alias("xml", TextMessage.class);
        String xmlString = xStream.toXML(message);
        return xmlString;

    }

    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent(content);
        return textMessageToXml(textMessage);
    }

    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("请选择：\n");
        sb.append("1.第一篇\n");
        sb.append("2.历史文章\n");
        sb.append("3.测试图文消息\n");
        return sb.toString();
    }

    public static String welcomeText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎关注 kindlePocket!\n\n");
        sb.append("请选择：\n");
        sb.append("1.第一篇\n");
        sb.append("2.历史文章\n");
        sb.append("3.测试图文消息\n");
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("等待推送第一篇文章...");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("等待推送历史文章列表...");
        return sb.toString();
    }

    /**
     * transfer picTextMessage to xml
     * 
     * @param picTextMessage
     * @return
     */
    public static String picTextMessageToXml(PicTextMessage picTextMessage) {

        XStream xStream = new XStream();
        xStream.alias("xml", PicTextMessage.class);
        xStream.alias("item", PicText.class);
        String xmlString = xStream.toXML(picTextMessage);
        return xmlString;
    }

    public static String initPicTextMessage(String toUserName, String fromUserName) {
        String message = null;
        List<PicText> picTextList = new ArrayList<PicText>();
        PicTextMessage picTextMessage = new PicTextMessage();

        PicText picText = new PicText();
        picText.setTitle("kindlePocket");
        picText.setDescription("kindle text books sharing platform");
        picText.setPicUrl("http://33051bbe.nat123.net/Weixin/WEB-INF/imgs/welcome.jpg");
        picText.setUrl("www.nasuf.cn");

        picTextList.add(picText);

        picTextMessage.setToUserName(fromUserName);
        picTextMessage.setFromUserName(toUserName);
        picTextMessage.setCreateTime(new Date().getTime());
        picTextMessage.setMsgType(MESSAGE_PIC_TEXT);
        picTextMessage.setArticles(picTextList);
        picTextMessage.setArticleCount(picTextList.size());

        message = picTextMessageToXml(picTextMessage);
        return message;
    }

    public static String initPicTextMessage(String toUserName, String fromUserName, List<String> titles) {
        String message = null;
        List<PicText> picTextList = new ArrayList<PicText>();
        PicTextMessage picTextMessage = new PicTextMessage();

        if (titles.size() > 0) {
            for (String title : titles) {
                PicText picText = new PicText();
                picText.setTitle(title);
                picText.setDescription("kindle text books sharing platform");
                picText.setPicUrl("http://33051bbe.nat123.net/imgs/welcome.jpg");
                picText.setUrl("http://44055713.nat123.net/bookManage/findall?title=" + title);
                picText.setUrl(picText.getUrl().replace("\"", ""));
                System.out.println("url:" + picText.getUrl());
                picTextList.add(picText);
            }
        } else {
            PicText picText = new PicText();
            picText.setTitle("Sorry, the textBook you searched is not instore yet");
            picText.setDescription("click and upload it :)");
            picText.setPicUrl("http://33051bbe.nat123.net/imgs/welcome.jpg");
            picText.setUrl("www.nasuf.cn");
            picTextList.add(picText);
        }

        picTextMessage.setToUserName(fromUserName);
        picTextMessage.setFromUserName(toUserName);
        picTextMessage.setCreateTime(new Date().getTime());
        picTextMessage.setMsgType(MESSAGE_PIC_TEXT);
        picTextMessage.setArticles(picTextList);
        picTextMessage.setArticleCount(picTextList.size());

        message = picTextMessageToXml(picTextMessage);
        return message;
    }
}
