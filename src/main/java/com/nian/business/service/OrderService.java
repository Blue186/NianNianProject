package com.nian.business.service;

import com.nian.business.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getTodayOrder(Integer businessID, Integer offset, Integer count);
    List<Order> getHistoryOrder(Integer businessID, Integer offset, Integer count);
    Order getOrderFromID(Integer businessID, Integer orderID);
}
