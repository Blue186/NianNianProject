package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Order;
import com.nian.business.mapper.OrderMapper;
import com.nian.business.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public List<Order> getTodayOrder(Integer businessID, Integer offset, Integer count) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();
        c.add(Calendar.DAY_OF_MONTH,1);
        Date tomorrow = c.getTime();


        var wrapper = new QueryWrapper<Order>();
        wrapper.ge("unix_timestamp(submit_time)", today.getTime()/1000);
        wrapper.lt("unix_timestamp(submit_time)", tomorrow.getTime()/1000);
        wrapper.eq("business_id", businessID);
        wrapper.orderByDesc("submit_time");

        if (offset != null && count != null){
            wrapper.last(String.format("limit %d offset %d", count, offset));
        }

        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getOrderStatistics(Integer businessID, Boolean history) {
        double money = 0.0;
        double pendingMoney = 0.0;
        int orderNums = 0;
        int pendingOrderNums = 0;

        // status 0: 已创建
        // status 1: 已提交
        // status 2: 已结算
        var orderStatistics = baseMapper.selectOrderStatistics(businessID, history);
        for (var orderStat: orderStatistics) {
            switch (orderStat.getStatus()){
                case 1:
                    pendingMoney = orderStat.getMoney();
                    pendingOrderNums = orderStat.getCount();
                    break;
                case 2:
                    money = orderStat.getMoney();
                    orderNums = orderStat.getCount();
                    break;
            }
        }

        var statisticsMap = new HashMap<String, Object>();
        statisticsMap.put("money", money);
        statisticsMap.put("order_nums", orderNums);
        statisticsMap.put("pending_money", pendingMoney);
        statisticsMap.put("pending_order_nums", pendingOrderNums);
        return statisticsMap;
    }

    @Override
    public List<Order> getHistoryOrder(Integer businessID, Integer offset, Integer count) {
        var wrapper = new QueryWrapper<Order>();
        wrapper.eq("business_id", businessID);
        wrapper.orderByDesc("submit_time");

        if (offset != null && count != null){
            wrapper.last(String.format("limit %d offset %d", count, offset));
        }

        return baseMapper.selectList(wrapper);
    }

    @Override
    public Order getOrderFromID(Integer businessID, Integer orderID) {
        var wrapper = new QueryWrapper<Order>();
        wrapper.eq("business_id", businessID);
        wrapper.eq("id", orderID);
        return baseMapper.selectOne(wrapper);
    }
}
