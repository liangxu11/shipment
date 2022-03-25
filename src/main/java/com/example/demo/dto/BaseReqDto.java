package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 13:11:04
 * @return * @return: null
 **/
@Data
@Accessors(chain = true)
public class BaseReqDto {

    @NotNull(message = "weight is not null")
    private Double weight;

    @NotEmpty(message = "orderId is not empty")
    private String orderId;

    private String remark;
}