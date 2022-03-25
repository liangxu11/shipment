package com.example.demo.service;

import com.example.demo.dto.*;
import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangxu 
 * @description 
 * @date 2022-03-16 15:27:31
 * @param * @Param null: 
 * @return * @return: null 
 **/
public interface ShipmentService {

    /**
     * 
     * @author iangxu
     * @date 2022/3/23 下午3:08
     * @param shipmentSplitReqDto 
     * @return java.util.ArrayList<com.example.demo.dto.ShipmentRespDto>
     */
    ArrayList<ShipmentRespDto> splitShipment(ShipmentSplitReqDto shipmentSplitReqDto);

    /**
     * 
     * @author liangxu
     * @date 2022-03-23 15:13:30 
 * @param baseReqDto
 * @return java.util.List<com.example.demo.dto.ShipmentRespDto>
     */
    List<ShipmentRespDto> changeQuantity(BaseReqDto baseReqDto);

    ShipmentRespDto mergeShipment(MergeReqDto mergeReqDto);

    ShipmentRespDto createTrade(TradeReqDto tradeReqDto);

    List<ShipmentRespDto> listShipments(String orderId);
}