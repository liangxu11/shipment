package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.ShipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangxu
 * @version 1.0
 * @description
 * @since 2022-03-16 09:49:58
 **/
@Api(tags = "shipment manage")
@RestController
@RequestMapping(value = "/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;


    /**
     * @author liangxu 
     * @description 
     * @date 2022-03-18 11:33:35
     * @param * @Param shipmentDto: 
 * @Param errors: 
     * @return * @return: com.example.demo.dto.Result 
     **/
    @ApiOperation("create trade")
    @PostMapping
    public Result create(@Valid @RequestBody TradeReqDto tradeReqDto, Errors errors) {

        ShipmentRespDto shipmentRespDto = shipmentService.createTrade(tradeReqDto);

        return new Result(shipmentRespDto);
    }

    @ApiOperation("shipment list")
    @GetMapping("/{orderId}")
    public Result list(@PathVariable("orderId") String orderId) {

        List<ShipmentRespDto> list= shipmentService.listShipments(orderId);

        return new Result(list);
    }

    /**
     * @param * @param shipmentDto:
     * @return * @return: com.example.demo.dto.Result
     * @author liangxu
     * @description
     * @date 2022-03-17 10:51:48
     * @Param errors:
     **/
    @ApiOperation("split goods")
    @PostMapping("/split")
    public Result split(@Valid @RequestBody ShipmentSplitReqDto shipmentSplitReqDto, Errors errors) {

        ArrayList<ShipmentRespDto> shipmentRespDtoList = shipmentService.splitShipment(shipmentSplitReqDto);

        return new Result(shipmentRespDtoList);
    }

    /**
     * @param * @param mergeReqDto:
     * @return * @return: com.example.demo.dto.Result
     * @author liangxu
     * @description
     * @date 2022-03-17 10:51:57
     * @Param errors:
     **/
    @ApiOperation("merge goods")
    @PostMapping("/merge")
    public Result merge(@Valid @RequestBody MergeReqDto mergeReqDto, Errors errors) {

        ShipmentRespDto ret = shipmentService.mergeShipment(mergeReqDto);

        return new Result(ret);
    }

    /**
     * @param * @param baseReqDto:
     * @return * @return: com.example.demo.dto.Result
     * @author liangxu
     * @description
     * @date 2022-03-17 10:52:00
     * @Param errors:
     **/
    @ApiOperation("change weight")
    @PostMapping("/change")
    public Result change(@Valid @RequestBody BaseReqDto baseReqDto, Errors errors) {

        List<ShipmentRespDto> shipmentRespDtos = shipmentService.changeQuantity(baseReqDto);

        return new Result(shipmentRespDtos);
    }


}