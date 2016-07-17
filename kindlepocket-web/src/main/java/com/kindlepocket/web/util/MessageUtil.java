package com.kindlepocket.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kindlepocket.web.controller.KindlePocketController;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kindlepocket.web.pojo.PicText;
import com.kindlepocket.web.pojo.PicTextMessage;
import com.kindlepocket.web.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    private static Logger logger = Logger.getLogger(MessageUtil.class);

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

    public static final String KINDLE_POCKET_HOST = "http://6bd9895a.ngrok.io";

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

    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("请选择：\n");
        sb.append("1. 关于kindlePocket\n");
        sb.append("2. 绑定步骤\n");
        return sb.toString();
    }

    public static String welcomeText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎关注 kindlePocket!\n\n");
        sb.append("请选择：\n");
        sb.append("1. 关于kindlePocket\n");
        sb.append("2. 绑定步骤\n");
        return sb.toString();
    }

   /* public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("等待推送第一篇文章...");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("等待推送历史文章列表...");
        return sb.toString();
    }
*/
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

    public static String initSinglePicTextMessage(String toUserName, String fromUserName, String title, String description, String picUrl, String url) {
        String message = null;
        List<PicText> picTextList = new ArrayList<PicText>();
        PicTextMessage picTextMessage = new PicTextMessage();

        PicText picText = new PicText();
        picText.setTitle(title);
        picText.setDescription(description);
        picText.setPicUrl(KINDLE_POCKET_HOST+picUrl);
        picText.setUrl(KINDLE_POCKET_HOST+url);

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

    public static String initSearchResultsPicTextMessage(String toUserName, String fromUserName, List<String> titles, List<String> idList, String queryParam) {
        String message = null;
        List<PicText> picTextList = new ArrayList<PicText>();
        PicTextMessage picTextMessage = new PicTextMessage();

        if (titles.size() > 0) {
            for (int i = 0; i < 5; i++) {
                PicText picText = new PicText();
                picText.setTitle(titles.get(i));
                picText.setDescription("kindle text books sharing platform");
                picText.setPicUrl(KINDLE_POCKET_HOST+"/imgs/welcome.jpg");
                // picText.setUrl("http://44055713.nat123.net/bookManage/findall?title=" + title);
                // picText.setUrl(picText.getUrl().replace("\"", ""));
                picText.setUrl(KINDLE_POCKET_HOST+"/KindlePocket/toDetailsPage?single=true&idList="+idList.get(i)+"&queryParam="+queryParam);
                System.out.println("url:" + picText.getUrl());
                picTextList.add(picText);
            }
            PicText picText = new PicText();
            picText.setTitle("共找到" + titles.size() + "本书，点击查看全部");
            picText.setDescription("kindle text books sharing platform");
            picText.setPicUrl(KINDLE_POCKET_HOST+"/imgs/welcome.jpg");
            // picText.setUrl("http://44055713.nat123.net/bookManage/findall?title=" + title);
            // picText.setUrl(picText.getUrl().replace("\"", ""));
            picText.setUrl(KINDLE_POCKET_HOST+"/KindlePocket/toDetailsPage?single=false&idList=all&queryParam=" + queryParam);
            System.out.println("url:" + picText.getUrl());
            picTextList.add(picText);
        } else {
            PicText picText = new PicText();
            picText.setTitle("Sorry, the textBook you searched is not instore yet");
            picText.setDescription("click and upload it :)");
            picText.setPicUrl(KINDLE_POCKET_HOST+"/imgs/welcome.jpg");
            // picText.setUrl("www.nasuf.cn");
            picText.setUrl(KINDLE_POCKET_HOST+"/KindlePocket/homepage");
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
