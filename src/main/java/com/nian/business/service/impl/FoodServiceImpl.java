package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Food;
import com.nian.business.mapper.FoodMapper;
import com.nian.business.service.FoodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Resource
    private FoodMapper foodMapper;
    public int insert(Food food){
        return foodMapper.insert(food);
    }
    public Food select(int id){
        return foodMapper.selectById(id);
    }

    @Override
    public int update(Food food) {
        return foodMapper.updateById(food);
    }
}
