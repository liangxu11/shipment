package com.example.demo.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.ShipmentBean;
import com.example.demo.bean.TradeBean;
import com.example.demo.dto.*;
import com.example.demo.exception.OpenapiBizException;
import com.example.demo.mapper.ShipmentMapper;
import com.example.demo.mapper.TradeMapper;
import com.example.demo.service.ShipmentService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author liangxu
 * @Date 2022/3/16下午2:23
 * @Version 1.2
 **/
@Service
@Transactional
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private ShipmentMapper shipmentMapper;

    /**
     * @param * @Param shipmentDto:
     * @return * @return: void
     * @author liangxu
     * @description Create a trading
     * @date 2022-03-17 17:19:19
     **/
    @Override
    public ShipmentRespDto createTrade(TradeReqDto tradeReqDto) {

        log.info("create trade request :{}", JSON.toJSON(tradeReqDto));

        TradeBean tradeBeanExist = tradeMapper.get(tradeReqDto.getOrderId());
        if (tradeBeanExist != null) {
            throw new OpenapiBizException("the trade has bean created");
        }

        Date date = new Date();
        // insert trade
        TradeBean addTradeBean = new TradeBean()
                .setWeight(tradeReqDto.getWeight())
                .setCreateDate(date)
                .setName(tradeReqDto.getGoodsName())
                .setRemark(tradeReqDto.getRemark())
                .setOrderId(tradeReqDto.getOrderId());

        tradeMapper.add(addTradeBean);

        long timeMillis = System.currentTimeMillis();
        //insert shipment
        ShipmentBean shipmentBean = new ShipmentBean()
                .setWeight(tradeReqDto.getWeight())
                .setCreateDate(date)
                .setTradeId(addTradeBean.getId())
                .setCourierNumber(String.valueOf(timeMillis));

        shipmentMapper.add(shipmentBean);

        ShipmentRespDto shipmentRespDto = new ShipmentRespDto()
                .setWeight(tradeReqDto.getWeight())
                .setCourierNumber(String.valueOf(timeMillis));
        return shipmentRespDto;
    }

    @Override
    public List<ShipmentRespDto> listShipments(String orderId) {

        TradeBean tradeBean = tradeMapper.get(orderId);
        if (tradeBean == null) {
            throw new OpenapiBizException("invalid orderId");
        }
        List<ShipmentBean> list = shipmentMapper.list(tradeBean.getId());
        ArrayList<ShipmentRespDto> shipmentRespDtos = Lists.newArrayList();
        for (ShipmentBean shipmentBean : list) {
            shipmentRespDtos.add(new ShipmentRespDto().setWeight(shipmentBean.getWeight())
                    .setCourierNumber(shipmentBean.getCourierNumber()));
        }
        return shipmentRespDtos;
    }

    /**
     * @param * @Param shipmentDto:
     * @return * @return: java.util.ArrayList<com.example.demo.dto.ShipmentRespDto>
     * @author liangxu
     * @description Split operation on a shipment, would create more than one shipments with specified quantities.
     * Sum of all child shipment quantities should be equal to parent shipment quantity.
     * @date 2022-03-17 12:54:48
     **/
    @Override
    public ArrayList<ShipmentRespDto> splitShipment(ShipmentSplitReqDto shipmentSplitReqDto) {

        log.info("split shipment request :{}", JSON.toJSON(shipmentSplitReqDto));

        List<Double> splitWeightList = shipmentSplitReqDto.getSplitWeightList();
        Double totalWeight = splitWeightList.stream().reduce(Double::sum).orElse(0D);

        //select trade and check
        List<ShipmentBean> shipmentBeans = shipmentMapper.listByCourierNumberList(Lists.newArrayList(shipmentSplitReqDto.getCourierNumber()));
        if (CollectionUtils.isEmpty(shipmentBeans)) {
            throw new OpenapiBizException("invalid shipmentId");
        }
        ShipmentBean shipmentBeanExist = shipmentBeans.get(0);
        if (!totalWeight.equals(shipmentBeanExist.getWeight())) {
            throw new OpenapiBizException("The weight discrepancy");
        }

        ArrayList<ShipmentBean> shipmentBeanArrayList = new ArrayList<>();
        ArrayList<ShipmentRespDto> respDtoList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();

        Date date = new Date();

        for (int i = 0; i < splitWeightList.size(); i++) {
            ShipmentBean shipmentBean = new ShipmentBean()
                    .setCourierNumber(String.valueOf(timestamp + i))
                    .setWeight(splitWeightList.get(i))
                    .setTradeId(shipmentBeanExist.getTradeId())
                    .setCreateDate(date);
            shipmentBeanArrayList.add(shipmentBean);

            ShipmentRespDto shipmentRespDto = new ShipmentRespDto()
                    .setWeight(splitWeightList.get(i))
                    .setCourierNumber(String.valueOf(timestamp + i));

            respDtoList.add(shipmentRespDto);
        }
        //delete old shipment
        shipmentMapper.deleteByIds(Lists.newArrayList(shipmentBeanExist.getId()));
        log.info("split , delete shipment ids :{}", JSON.toJSON(Lists.newArrayList(shipmentBeanExist.getId())));

        // insert new shipment
        shipmentMapper.insertList(shipmentBeanArrayList);
        return respDtoList;
    }

    /**
     * @param * @Param mergeReqDto:
     * @return * @return: com.example.demo.dto.ShipmentRespDto
     * @author liangxu
     * @description Merge operation on more than one shipment, would create one child shipment with summed
     * up quantity. Sum of all parent shipment quantities should be equal to child shipment quantity.
     * @date 2022-03-17 12:55:02
     **/
    @Override
    public ShipmentRespDto mergeShipment(MergeReqDto mergeReqDto) {

        log.info("merge shipment request :{}", JSON.toJSON(mergeReqDto));

        List<String> courierNumberList = mergeReqDto.getCourierNumberList();

        //select
        List<ShipmentBean> shipmentBeanList = shipmentMapper.listByCourierNumberList(courierNumberList);
        if (shipmentBeanList == null || shipmentBeanList.size() < courierNumberList.size()) {
            throw new OpenapiBizException("Invalid courierNumber exists");
        }

        long count = shipmentBeanList.stream().map(ShipmentBean::getTradeId).distinct().count();
        if (count > 1) {
            throw new OpenapiBizException("Different orders cannot be combined");
        }

        double sum = shipmentBeanList.stream().mapToDouble(ShipmentBean::getWeight).sum();
        //delete
        List<Integer> shipmentIds = shipmentBeanList.stream().map(ShipmentBean::getId).collect(Collectors.toList());
        shipmentMapper.deleteByIds(shipmentIds);
        log.info("merge , delete shipment ids :{}", JSON.toJSON(shipmentIds));

        //insert
        long timeMillis = System.currentTimeMillis();
        ShipmentBean ShipmentBean = new ShipmentBean()
                .setWeight(sum)
                .setCourierNumber(String.valueOf(timeMillis))
                .setCreateDate(new Date())
                .setTradeId(shipmentBeanList.get(0).getTradeId());
        shipmentMapper.insertList(Lists.newArrayList(ShipmentBean));
        return new ShipmentRespDto().setCourierNumber(String.valueOf(timeMillis)).setWeight(sum);
    }

    /**
     * @param * @Param baseReqDto:
     * @return * @return: void
     * @author liangxu
     * @description This operation applies to trade. When trade quantity is changed, all shipment quantities should
     * be updated proportionally
     * @date 2022-03-17 12:55:07
     **/
    @Override
    public List<ShipmentRespDto> changeQuantity(BaseReqDto baseReqDto) {

        log.info("changeQuantity request :{}", JSON.toJSON(baseReqDto));

        TradeBean tradeBean = tradeMapper.get(baseReqDto.getOrderId());

        if (tradeBean == null) {
            throw new OpenapiBizException("Invalid orderId");
        }

        int tradeId = tradeBean.getId();

        List<ShipmentBean> ShipmentBeanList = shipmentMapper.list(tradeId);

        Double weight = baseReqDto.getWeight();
        tradeMapper.update(new TradeBean().setOrderId(baseReqDto.getOrderId()).setWeight(weight).setRemark(baseReqDto.getRemark()));

        double rate = weight / tradeBean.getWeight();
        ArrayList<ShipmentRespDto> respDtoList = new ArrayList<>();
        ShipmentBeanList.forEach(shipmentBean -> {
            shipmentBean.setWeight(rate * shipmentBean.getWeight());
            shipmentMapper.update(shipmentBean);

            ShipmentRespDto shipmentRespDto = new ShipmentRespDto()
                    .setWeight(shipmentBean.getWeight())
                    .setCourierNumber(shipmentBean.getCourierNumber());

            respDtoList.add(shipmentRespDto);
        });
        return respDtoList;

    }


}