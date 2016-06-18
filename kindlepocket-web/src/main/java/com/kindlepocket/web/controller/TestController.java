package com.kindlepocket.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lingzhiyuan.
 * Date : 16/6/18.
 * Time : 20:13.
 * Description:
 */

@Controller
public class TestController {

    @RequestMapping("/test")
    public String toTestPage(){
        return "test";
    }
}
