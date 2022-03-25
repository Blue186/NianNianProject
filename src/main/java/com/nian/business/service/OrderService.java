package com.nian.business.service;

import com.nian.business.entity.Order;
import com.nian.business.entity.vo.order.OrderItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    List<OrderItem> getTodayOrder(Integer businessID);
    List<OrderItem> getHistoryOrder(Integer businessID);
}
