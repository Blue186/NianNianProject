package com.nian.business.controller.business;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.service.OrderFoodService;
import com.nian.business.service.OrderService;
import com.nian.business.service.RoomService;
import com.nian.business.utils.JsonUtil;
import com.nian.business.utils.R;
import lombok.var;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business")
@Validated
public class OrderController {
    @Resource
    OrderService orderService;
    @Resource
    RoomService roomService;
    @Resource
    OrderFoodService orderFoodService;

    @GetMapping("/order/today")
    public R<?> getTodayOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam @Min(value = 0, message = "offset >= 0") Integer offset,
            @RequestParam @Range(min = 0, max = 10, message = "0 <= count <= 10") Integer count
    ){
        Business business = (Business) request.getAttribute("business");

        // 组装orders
        var ordersMap = new ArrayList<Map<String, Object>>();
        var orders = orderService.getTodayOrder(business.getId(), offset, count);
        for (var order: orders){
            var room = roomService.selectRoom(business.getId(), order.getRoomId());
            var roomMap = new HashMap<String, Object>();
            roomMap.put("name", room.getName());
            roomMap.put("id", room.getId());

            var foodsMap = new ArrayList<Map<String, Object>>();

            double foodsMoney =  0.0;
            var foods = orderFoodService.getOrderFoods(order.getId());
            for (var food: foods){
                foodsMoney += food.getPrice() * food.getFoodNums();

                var foodMap = new HashMap<String, Object>();
                foodMap.put("name", food.getName());
                foodMap.put("count", food.getFoodNums());
                foodMap.put("price", food.getPrice());
                foodsMap.add(foodMap);
            }

            var orderMap = new HashMap<String, Object>();
            orderMap.put("id", order.getId());
            orderMap.put("room", roomMap);
            orderMap.put("foods", foodsMap);
            orderMap.put("total_price", foodsMoney);
            orderMap.put("create_time", order.getCreateTime());
            orderMap.put("submit_time", order.getSubmitTime());
            ordersMap.add(orderMap);
        }

        // 组装statistics
        var statisticsMap = orderService.getOrderStatistics(business.getId(), false);

        // 组装detail
        var detailJson = new JSONObject();
        detailJson.set("orders", ordersMap);
        detailJson.set("statistics", statisticsMap);

        return R.ok().detail(detailJson);
    }

    @GetMapping("/order/history")
    public R<?> getHistoryOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam @Min(value = 0, message = "offset >= 0") Integer offset,
            @RequestParam @Range(min = 0, max = 10, message = "0 <= count <= 10") Integer count
    ){
        Business business = (Business) request.getAttribute("business");

        // 组装orders
        var ordersJson = new ArrayList<JSONObject>();
        var orders = orderService.getHistoryOrder(business.getId(), offset, count);
        for (var order: orders){
            var room = roomService.selectRoom(business.getId(), order.getRoomId());
            var roomJson = new JSONObject();
            roomJson.set("name", room.getName());
            roomJson.set("id", room.getId());

            var foodsJson = new ArrayList<JSONObject>();

            double foodsMoney =  0.0;
            var foods = orderFoodService.getOrderFoods(order.getId());
            for (var food: foods){
                foodsMoney += food.getPrice() * food.getFoodNums();

                var foodJson = new JSONObject();
                foodJson.set("name", food.getName());
                foodJson.set("count", food.getFoodNums());
                foodJson.set("price", food.getPrice());
                foodsJson.add(foodJson);
            }

            var orderJson = new JSONObject();
            orderJson.set("id", order.getId());
            orderJson.set("room", roomJson);
            orderJson.set("foods", foodsJson);
            orderJson.set("total_price", foodsMoney);
            orderJson.set("create_time", order.getCreateTime());
            orderJson.set("submit_time", order.getSubmitTime());
            ordersJson.add(orderJson);
        }

        // 组装statistics
        var statisticsJson = orderService.getOrderStatistics(business.getId(), true);

        // 组装detail
        var detailJson = new JSONObject();
        detailJson.set("orders", ordersJson);
        detailJson.set("statistics", statisticsJson);

        return R.ok().detail(detailJson);
    }

    @GetMapping("/order/{orderID}")
    public R<?> getOrderDetail(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer orderID
    ){
        Business business = (Business) request.getAttribute("business");

        var order = orderService.getOrderFromID(business.getId(), orderID);
        if(order == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("获取订单失败");
        }

        double totalPrice = 0.0;
        var orderFoods = orderFoodService.getOrderFoods(order.getId());
        var foodsJson = new ArrayList<JSONObject>();
        for(var food: orderFoods){
            var foodJson = new JSONObject();
            foodJson.set("name", food.getName());
            foodJson.set("count", food.getFoodNums());
            foodJson.set("price", food.getPrice());
            totalPrice += food.getPrice() * food.getFoodNums();
            foodsJson.add(foodJson);
        }

        var room = roomService.selectRoom(business.getId(), order.getRoomId());
        var roomJson = new JSONObject();
        roomJson.set("name", room.getName());
        roomJson.set("id", room.getId());

        var detailJson = new JSONObject();
        detailJson.set("total_price", totalPrice);
        detailJson.set("create_time", order.getCreateTime());
        detailJson.set("submit_time", order.getSubmitTime());
        detailJson.set("room", roomJson);
        detailJson.set("foods", foodsJson);

        return R.ok().detail(detailJson);
    }
}
