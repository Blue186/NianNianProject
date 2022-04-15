package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Food;
import com.nian.business.entity.Order;
import com.nian.business.entity.OrderFood;

import java.util.Date;
import java.util.List;

public interface OrderFoodService extends IService<OrderFood> {
    List<OrderFood> getOrderFoods(Integer orderID);
    Integer cancelOrderFood(Order order, Integer foodID, Date submitTime);
    Integer appendOrderFood(Order order, List<Food> foods);
}
