package com.nian.business.service;

import com.nian.business.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getTodayOrder(Integer businessID);
    List<Order> getHistoryOrder(Integer businessID);
}
