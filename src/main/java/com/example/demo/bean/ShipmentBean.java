package com.example.demo.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 15:09:04
 * @return * @return: null
 **/
@Data
@Accessors(chain = true)
public class ShipmentBean {

    private Integer id;

    private Integer tradeId;

    private Double weight;

    private String courierNumber;

    private Date CreateDate;

}
