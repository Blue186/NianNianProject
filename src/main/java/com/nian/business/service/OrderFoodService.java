package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Food;
import com.nian.business.entity.Order;
import com.nian.business.entity.OrderFood;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderFoodService extends IService<OrderFood> {
    List<OrderFood> getOrderFoods(Integer orderID);
    Integer cancelOrderFood(Order order, Integer foodID, Date submitTime);
    Boolean appendOrderFood(Order order, List<Pair<Food, Integer>> foodAndCountMap);
}
