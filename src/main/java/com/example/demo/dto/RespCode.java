package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 14:11:23
 * @return * @return: null
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class RespCode implements Serializable {


    private String code = "1";

    private String message = "success";

    public void setRespCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
