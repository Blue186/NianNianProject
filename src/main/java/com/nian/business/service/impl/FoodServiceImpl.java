package com.nian.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Category;
import com.nian.business.entity.Food;
import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.food.*;
import com.nian.business.mapper.FoodMapper;
import com.nian.business.service.CategoryService;
import com.nian.business.service.FoodService;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Resource
    private CategoryService categoryService;
    public int insertFood(FoodNoId foodNoId,Integer businessId){
        Category category = categoryService.selectOne(businessId, foodNoId.getCategoryId());
        if(category==null){
            return 0;
        }
        Food food=new Food();
        food.setName(foodNoId.getName());
        food.setImage(foodNoId.getImage());
        food.setIntroduce(foodNoId.getIntroduce());
        food.setCategoryId(category.getId());
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

    @Override
    public JSONObject selectFood(Integer businessId, Integer foodId) {
        QueryWrapper<Food> wrapper = new QueryWrapper<Food>().eq("id",foodId)
                .eq("business_id",businessId);
        Food food = baseMapper.selectOne(wrapper);
        if(food == null){
            return null;
        }
        FoodNoIds foodNoIds=new FoodNoIds();
        foodNoIds.setImage(food.getImage());
        foodNoIds.setStatus(food.getStatus());
        foodNoIds.setIntroduce(food.getIntroduce());
        foodNoIds.setName(food.getName());
        foodNoIds.setPrice(food.getPrice());

        Integer categoryId = food.getCategoryId();
        Category category = categoryService.selectOne(businessId, categoryId);

        CategoryIdName categoryIdName=new CategoryIdName();
        categoryIdName.setId(category.getId());
        categoryIdName.setName(category.getName());

        JSONObject jsonObject=new JSONObject();
        jsonObject.set("food",foodNoIds);
        jsonObject.set("category",categoryIdName);
        return jsonObject;
    }

    @Override
    public Food getFoodFromID(Integer businessID, Integer foodID) {
        var wrapper = new QueryWrapper<Food>();
        wrapper.eq("business_id", businessID);
        wrapper.eq("id", foodID);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Food> getFoodsFromIDList(Integer businessID, List<Integer> idList) {
         var foods = baseMapper.selectBatchIds(idList);
         for (var food: foods){
             if (!food.getBusinessId().equals(businessID)){
                 return null;
             }
         }
        return foods;
    }
}
