package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 11:13:23
 * @return * @return: null
 **/
@Data
@Accessors(chain = true)
public class ShipmentRespDto {

    private String courierNumber;

    private Double weight;

}