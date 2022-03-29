package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Food;
import com.nian.business.entity.vo.food.FoodMenuItem;
import com.nian.business.entity.vo.food.FoodNoId;
import com.nian.business.entity.vo.food.FoodNoStatus;
import com.nian.business.entity.vo.food.FoodStatus;

import java.util.List;

public interface FoodService extends IService<Food> {
    int insertFood(FoodNoId foodNoId,Integer businessId);
    int updateFoodNoStatus(FoodNoStatus foodNoStatus,Integer food_id,Integer businessId);
    int updateFoodStatus(FoodStatus foodStatus,Integer food_id,Integer businessId);
    List<FoodMenuItem> selectAll(Integer businessID);
}
