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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/business")
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
            @RequestParam Integer count,
            @RequestParam Integer offset
    ){
        Business business = (Business) request.getAttribute("business");
        double money = 0.0, pendingMoney = 0.0;
        int orderNums = 0, pendingOrderNums = 0;

        // 组装orders
        var ordersJson = new ArrayList<JSONObject>();
        var orders = orderService.getTodayOrder(business.getId());
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

            switch (order.getStatus()){
                case 1: // 待支付
                    pendingOrderNums++;
                    pendingMoney += foodsMoney;
                    break;
                case 2: // 已结束
                    orderNums++;
                    money += foodsMoney;
                    break;
            }

            var orderJson = new JSONObject();
            orderJson.set("room", roomJson);
            orderJson.set("foods", foodsJson);
            orderJson.set("total_price", foodsMoney);
            orderJson.set("create_time", order.getCreateTime());
            orderJson.set("submit_time", order.getSubmitTime());
            ordersJson.add(orderJson);
        }

        // 组装statistics
        var statisticsJson = new JSONObject();
        statisticsJson.set("money", money);
        statisticsJson.set("order_nums", orderNums);
        statisticsJson.set("pending_money", pendingMoney);
        statisticsJson.set("pending_order_nums", pendingOrderNums);

        // 组装detail
        var detailJson = new JSONObject();
        detailJson.set("orders", ordersJson);
        detailJson.set("statistics", statisticsJson);

        return R.ok().detail(detailJson);
    }

    @GetMapping("/order/history")
    public R<?> getHistoryOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Integer count,
            @RequestParam Integer offset
    ){
        Business business = (Business) request.getAttribute("business");
        double money = 0.0, pendingMoney = 0.0;
        int orderNums = 0, pendingOrderNums = 0;

        // 组装orders
        var ordersJson = new ArrayList<JSONObject>();
        var orders = orderService.getHistoryOrder(business.getId());
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

            switch (order.getStatus()){
                case 1: // 待支付
                    pendingOrderNums++;
                    pendingMoney += foodsMoney;
                    break;
                case 2: // 已结束
                    orderNums++;
                    money += foodsMoney;
                    break;
            }

            var orderJson = new JSONObject();
            orderJson.set("room", roomJson);
            orderJson.set("foods", foodsJson);
            orderJson.set("total_price", foodsMoney);
            orderJson.set("create_time", order.getCreateTime());
            orderJson.set("submit_time", order.getSubmitTime());
            ordersJson.add(orderJson);
        }

        // 组装statistics
        var statisticsJson = new JSONObject();
        statisticsJson.set("money", money);
        statisticsJson.set("order_nums", orderNums);
        statisticsJson.set("pending_money", pendingMoney);
        statisticsJson.set("pending_order_nums", pendingOrderNums);

        // 组装detail
        var detailJson = new JSONObject();
        detailJson.set("orders", ordersJson);
        detailJson.set("statistics", statisticsJson);

        return R.ok().detail(detailJson);
    }
}
