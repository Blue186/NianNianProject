package com.nian.business.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Food;
import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.food.*;

import java.util.List;

public interface FoodService extends IService<Food> {
    int insertFood(FoodNoId foodNoId,Integer businessId);
    int updateFoodNoStatus(FoodNoStatus foodNoStatus,Integer food_id,Integer businessId);
    int updateFoodStatus(FoodStatus foodStatus,Integer food_id,Integer businessId);
    List<FoodMenuItem> selectAll(Integer businessID);
    JSONObject selectFood(Integer businessId, Integer foodId);
    Food getFoodFromID(Integer businessID, Integer foodID);
}
