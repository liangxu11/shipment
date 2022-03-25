package com.example.demo.exception;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 14:02:57
 * @return * @return: null
 **/
public enum OpenApiRespEnum {

    Fail(-1, "Internal error"),
    Success(1, "success"),
    ReqParamEntityNotExist(2, "The parameter query entity does not exist"),
    ReqParamNotVaild(3, "Invalid parameter："),
    ReqParamOversize(4, "The number of parameters exceeds 1000");

    int code;
    String message;

    OpenApiRespEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
