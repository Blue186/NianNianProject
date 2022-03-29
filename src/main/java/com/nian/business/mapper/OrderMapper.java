package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.nian.business.entity.Order;
import com.nian.business.entity.vo.food.FoodItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT * FROM user ${w.customSqlSegment}")
    List<Order> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<Order> w);

    @Select("select order_foods.food_nums as count, order_foods.remark, food.name, food.price\n" +
            "from order_foods\n" +
            "inner join food\n" +
            "on food.id = order_foods.food_id\n" +
            "where order_foods.order_id = #{order_id}")
    List<FoodItem> selectOrderFoods(@Param("order_id") Integer orderID);
}
