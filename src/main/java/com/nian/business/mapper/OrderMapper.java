package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.Order;
import com.nian.business.entity.vo.food.FoodItem;
import com.nian.business.entity.vo.statistics.StatisticsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select order_foods.food_nums as count, order_foods.remark, food.name, food.price\n" +
            "from order_foods\n" +
            "inner join food\n" +
            "on food.id = order_foods.food_id\n" +
            "where order_foods.order_id = #{orderID}")
    List<FoodItem> selectOrderFoods(@Param("orderID") Integer orderID);

    @Select("select o.status, IFNULL(sum(o_f.price * o_f.food_nums*!o_f.cancel), 0) money, count(distinct o.id) as count\n" +
            "from `order` o left join order_foods o_f on o.id = o_f.order_id where o.business_id= #{businessID} and (#{history} or date(o.submit_time) = curdate())\n" +
            "group by o.status")
    List<StatisticsOrder> selectOrderStatistics(@Param("businessID") Integer businessID, @Param("history") Boolean history);
}
