package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-17 13:13:13
 * @return * @return: null
 **/
@Data
@Accessors(chain = true)
public class TradeReqDto extends BaseReqDto {

    @NotEmpty(message = "goodsName is not empty")
    private String goodsName;


}