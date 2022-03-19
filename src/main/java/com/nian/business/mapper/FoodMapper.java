package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.Food;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
