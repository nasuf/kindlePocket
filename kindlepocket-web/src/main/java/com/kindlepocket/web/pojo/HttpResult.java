package com.kindlepocket.web.pojo;

import org.apache.commons.lang3.StringUtils;

public class HttpResult {

    // response code
    private Integer code;

    // response body
    private String body;

    public HttpResult() {

    }

    public HttpResult(Integer code, String body) {
        super();
        this.code = code;
        if (StringUtils.isNotEmpty(body)) {
            this.body = body;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
