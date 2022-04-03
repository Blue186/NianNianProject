package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
            wrapper.last("limit "+ count + " offset " + offset);
        }

        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Order> getHistoryOrder(Integer businessID, Integer offset, Integer count) {
        var wrapper = new QueryWrapper<Order>();
        wrapper.eq("business_id", businessID);
        wrapper.orderByDesc("submit_time");

        if (offset != null && count != null){
            wrapper.last("limit "+ count + " offset " + offset);
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
