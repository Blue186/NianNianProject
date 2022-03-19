package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.OrderFood;
import com.nian.business.mapper.OrderFoodMapper;
import com.nian.business.service.OrderFoodService;
import org.springframework.stereotype.Service;

@Service
public class OrderFoodServiceImpl extends ServiceImpl<OrderFoodMapper, OrderFood> implements OrderFoodService {
}
