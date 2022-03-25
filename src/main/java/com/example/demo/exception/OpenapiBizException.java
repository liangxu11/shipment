package com.example.demo.exception;


/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 13:07:58
 * @return * @return: null
 **/
public class OpenapiBizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MSG = "The system is abnormal. Please try again later";

    private int code = OpenApiRespEnum.Fail.getCode();

    private boolean sendSms = false;

    public OpenapiBizException() {
        super(DEFAULT_MSG);
    }

    public OpenapiBizException(String message) {
        super(message);
    }

    public OpenapiBizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public OpenapiBizException(int code, String message, boolean sendSms) {
        super(message);
        this.code = code;
        this.sendSms = sendSms;
    }

    public OpenapiBizException(Throwable cause) {
        super(cause);
    }

    public OpenapiBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenapiBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public boolean isSendSms() {
        return sendSms;
    }
}
