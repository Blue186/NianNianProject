package com.nian.business.service;

import com.nian.business.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> getTodayOrder(Integer businessID, Integer offset, Integer count);
    List<Order> getHistoryOrder(Integer businessID, Integer offset, Integer count);
    Map<String, Object> getOrderStatistics(Integer businessID, Boolean history);
    Order getOrderFromID(Integer businessID, Integer orderID);
}
