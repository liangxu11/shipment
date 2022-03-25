package com.example.demo.exception;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 15:15:50
 * @return * @return: null
 **/
public class ParamInvalidException extends RuntimeException {

    public ParamInvalidException(String message) {
        super(message);
    }

    public ParamInvalidException(Throwable cause) {
        super(cause);
    }

    public ParamInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamInvalidException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
