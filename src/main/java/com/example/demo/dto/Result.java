package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 13:13:03
 * @return * @return: null
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)

public class Result<T> implements Serializable {


    private RespCode respCode = new RespCode();


    private T data;


    public Result(String code, String message) {
        setRespCode(code, message);
    }

    public Result(String code, String message, T data) {
        setRespCode(code, message);
        this.data = data;
    }

    public Result(T data) {
        this.data = data;
    }

    /**
     * Set the status code and status prompt
     *
     * @param code
     * @param message
     */
    public void setRespCode(String code, String message) {
        this.respCode.setRespCode(code, message);
    }

}
