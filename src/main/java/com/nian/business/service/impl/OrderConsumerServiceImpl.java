package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.OrderConsumer;
import com.nian.business.mapper.OrderConsumerMapper;
import com.nian.business.service.OrderConsumerService;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumerServiceImpl extends ServiceImpl<OrderConsumerMapper, OrderConsumer> implements OrderConsumerService {
}
