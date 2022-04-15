package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Food;
import com.nian.business.entity.Order;
import com.nian.business.entity.OrderFood;
import com.nian.business.mapper.OrderFoodMapper;
import com.nian.business.service.OrderFoodService;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderFoodServiceImpl extends ServiceImpl<OrderFoodMapper, OrderFood> implements OrderFoodService {
    @Override
    public List<OrderFood> getOrderFoods(Integer orderID) {
        var wrapper = new QueryWrapper<OrderFood>();
        wrapper.eq("order_id", orderID);
        // submit_time 不为0说明food已经被提交
        wrapper.ne("submit_time", 0);

        return baseMapper.selectList(wrapper);
    }

    @Override
    public Integer cancelOrderFood(Order order, Integer foodID, Date submitTime) {
        var wrapper = new QueryWrapper<OrderFood>();
        wrapper.eq("order_id", order.getId());
        wrapper.eq("food_id", foodID);
        wrapper.eq("submit_time", submitTime);

        var orderFood = new OrderFood();
        orderFood.setCancel(true);

        return baseMapper.update(orderFood, wrapper);
    }

    @Override
    public Integer appendOrderFood(Order order, List<Food> foods) {

        return null;
    }
}
