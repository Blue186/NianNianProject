package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Food;
import com.nian.business.mapper.FoodMapper;
import com.nian.business.service.FoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
}
