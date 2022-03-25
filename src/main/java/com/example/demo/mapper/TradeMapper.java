package com.example.demo.mapper;

import com.example.demo.bean.TradeBean;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @Author liangxu
 * @Date 2022/3/16下午3:05
 * @Version 1.2
 **/
@Repository
public interface TradeMapper {

    TradeBean get(String orderId);

    TradeBean getById(Integer id);

    void add(TradeBean tradeBean);

    void update(TradeBean tradeBean);

}