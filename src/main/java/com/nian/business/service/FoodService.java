package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Food;

public interface FoodService extends IService<Food> {
    int insert(Food food);
    Food select(int id);
    int update(Food food);
}
