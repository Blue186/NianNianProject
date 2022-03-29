package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Food;
import com.nian.business.entity.vo.food.FoodMenuItem;
import com.nian.business.entity.vo.food.FoodNoId;
import com.nian.business.entity.vo.food.FoodNoStatus;
import com.nian.business.entity.vo.food.FoodStatus;
import com.nian.business.mapper.FoodMapper;
import com.nian.business.service.FoodService;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    public int insertFood(FoodNoId foodNoId,Integer businessId){
        Food food=new Food();
        food.setName(foodNoId.getName());
        food.setImage(foodNoId.getImage());
        food.setIntroduce(foodNoId.getIntroduce());
        food.setCategoryId(foodNoId.getCategoryId());
        food.setPrice(foodNoId.getPrice());
        food.setStatus(foodNoId.getStatus());
        food.setBusinessId(businessId);
        return baseMapper.insert(food);
    }

    @Override
    public int updateFoodNoStatus(FoodNoStatus foodNoStatus,Integer food_id,Integer businessId) {
        Food food = new Food();
        food.setPrice(foodNoStatus.getPrice());
        food.setImage(foodNoStatus.getImage());
        food.setIntroduce(foodNoStatus.getIntroduce());
        food.setName(foodNoStatus.getName());
        food.setCategoryId(foodNoStatus.getCategoryId());
        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessId);
        map.put("id" ,food_id);
        return baseMapper.update(food,new QueryWrapper<Food>().allEq(map));
    }

    @Override
    public int updateFoodStatus(FoodStatus foodStatus,Integer food_id,Integer businessId) {
        Food food=new Food();
        food.setStatus(foodStatus.getStatus());
        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessId);
        map.put("id" ,food_id);
        return baseMapper.update(food,new QueryWrapper<Food>().allEq(map));
    }

    @Override
    public List<FoodMenuItem> selectAll(Integer businessID) {
        var foodMenuList = new ArrayList<FoodMenuItem>();
        var foods = baseMapper.selectList(new QueryWrapper<Food>().eq("business_id", businessID));
        for (var food: foods){
            var item = new FoodMenuItem();
            item.setId(food.getId());
            item.setName(food.getName());
            item.setImage(food.getImage());
            item.setIntroduce(food.getIntroduce());
            item.setPrice(food.getPrice());
            item.setStatus(food.getStatus());
            item.setCategoryId(food.getCategoryId());
            foodMenuList.add(item);
        }
        return foodMenuList;
    }
}
