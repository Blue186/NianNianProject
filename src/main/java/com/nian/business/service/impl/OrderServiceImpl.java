package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Order;
import com.nian.business.entity.vo.order.OrderItem;
import com.nian.business.mapper.OrderMapper;
import com.nian.business.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public List<OrderItem> getTodayOrder(Integer businessID) {
        return null;
    }

    @Override
    public List<OrderItem> getHistoryOrder(Integer businessID) {
        return null;
    }
}
