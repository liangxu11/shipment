package com.example.demo;

import com.example.demo.bean.ShipmentBean;
import com.example.demo.bean.TradeBean;
import com.example.demo.dto.*;
import com.example.demo.mapper.ShipmentMapper;
import com.example.demo.mapper.TradeMapper;
import com.example.demo.serviceImpl.ShipmentServiceImpl;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
class ShipmentServiceTests {

    @InjectMocks
    ShipmentServiceImpl shipmentService;

    @Mock
    ShipmentMapper shipmentMapper;
    @Mock
    TradeMapper tradeMapper;

    @Test
    void create() {
        TradeReqDto tradeReqDto = (TradeReqDto) new TradeReqDto()
                .setGoodsName("11")
                .setWeight(100D)
                .setOrderId("1005");

        when(tradeMapper.get(anyString())).thenReturn(null);

        ShipmentRespDto shipmentRespDto = shipmentService.createTrade(tradeReqDto);
        Assert.assertTrue(shipmentRespDto.getWeight().equals(100D));
    }

    @Test
    void split() {
        ShipmentSplitReqDto tradeSplitReqDto = new ShipmentSplitReqDto()
                .setCourierNumber("1")
                .setSplitWeightList(Lists.newArrayList(10.0, 20.0, 30.0));

        when(shipmentMapper.listByCourierNumberList(anyList())).thenReturn(Lists.newArrayList(new ShipmentBean().setTradeId(1).setWeight(60.0)));

        ArrayList<ShipmentRespDto> list = shipmentService.splitShipment(tradeSplitReqDto);
        Assert.assertTrue(list.size() == 3);
    }

    @Test
    void merge() {
        ArrayList<String>  courierNumberList= Lists.newArrayList("10", "11");
        MergeReqDto mergeReqDto = new MergeReqDto().setCourierNumberList(courierNumberList);

        when(shipmentMapper.listByCourierNumberList(anyList())).thenReturn(Lists.newArrayList(
                new ShipmentBean().setWeight(10.0).setTradeId(1), new ShipmentBean().setWeight(15.0).setTradeId(1)));

        ShipmentRespDto shipmentRespDto = shipmentService.mergeShipment(mergeReqDto);

        Assert.assertTrue(shipmentRespDto.getWeight().equals(25.0));
    }

    @Test
    void change() {
        String orderId = "100";
        BaseReqDto baseReqDto = new BaseReqDto().setRemark("1").setOrderId(orderId).setWeight(1000.0);
        when(tradeMapper.get(anyString())).thenReturn(new TradeBean().setId(1).setWeight(25.0));
        ArrayList<ShipmentBean> shipmentBeanArrayList = Lists.newArrayList(
                new ShipmentBean().setWeight(10.0).setTradeId(1), new ShipmentBean().setWeight(15.0).setTradeId(1));
        when(shipmentMapper.list(anyInt())).thenReturn(shipmentBeanArrayList);

        List<ShipmentRespDto> shipmentRespDtos = shipmentService.changeQuantity(baseReqDto);

        double sum = shipmentRespDtos.stream().mapToDouble(ShipmentRespDto::getWeight).sum();
        Assert.assertTrue(baseReqDto.getWeight()==sum);
    }
}
