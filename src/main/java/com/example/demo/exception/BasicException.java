package com.example.demo.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-17 13:14:19
 * @return * @return: null
 **/
@Getter
public class BasicException extends RuntimeException {

    private final int code;


    private final String msg;


    private final Map<String, Object> attachment = new HashMap();

    private BasicException() {
        this.code = -1;
        this.msg = "unknown error";
    }

    private BasicException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private BasicException(int code, String msg, Throwable e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    private BasicException(Throwable t) {
        super(t);
        this.code = -1;
        this.msg = "unknown error";
    }


    public static BasicException wrap() {
        return new BasicException();
    }

    public static BasicException wrap(int code, String msg) {
        return new BasicException(code, msg);
    }

    public static BasicException wrap(int code, String msg, Throwable e) {
        return new BasicException(code, msg, e);
    }

    public static BasicException wrap(Throwable e) {
        return new BasicException(e);
    }


    public BasicException set(String key, Object value) {
        this.attachment.put(key, value);
        return this;
    }


    public BasicException setAll(Map<String, Object> map) {
        this.attachment.putAll(map);
        return this;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("code=").append(code).append("\n")
                .append("msg=").append(msg).append("\n");
        attachment.forEach((k, v) -> builder.append(k).append("=").append(v).append("\n"));
        return builder.append(super.toString()).toString();
    }
}
