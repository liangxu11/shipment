package com.example.demo.mapper;

import com.example.demo.bean.ShipmentBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author liangxu
 * @Date 2022/3/16下午3:07
 * @Version 1.2
 **/
@Repository
public interface ShipmentMapper {

    ShipmentBean get(String name);

    ShipmentBean getById(Integer id);

    List<ShipmentBean> list(Integer tradeId);

    List<ShipmentBean> listByIds(List<Integer> idList);

    List<ShipmentBean> listByCourierNumberList(List<String> courierNumberList);

    void deleteByIds(List<Integer> idList);

    void deleteByTradeId(Integer tradeId);

    void add(ShipmentBean shipmentBean);

    void update(ShipmentBean shipmentBean);

    void insertList(List<ShipmentBean> list);
}