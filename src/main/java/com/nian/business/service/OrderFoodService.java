package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.OrderFood;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface OrderFoodService extends IService<OrderFood> {
    List<OrderFood> getOrderFoods(Integer orderID);
}
