package com.nian.business.controller.business;


import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.entity.Food;
import com.nian.business.entity.Order;
import com.nian.business.entity.vo.food.FoodIDCount;
import com.nian.business.service.FoodService;
import com.nian.business.service.OrderFoodService;
import com.nian.business.service.OrderService;
import com.nian.business.service.RoomService;
import com.nian.business.utils.R;
import com.nian.business.utils.RedissionUtil;
import javafx.util.Pair;
import lombok.var;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/business/order")
@Validated
public class OrderController {
    @Resource
    OrderService orderService;
    @Resource
    RoomService roomService;
    @Resource
    OrderFoodService orderFoodService;
    @Resource
    FoodService foodService;
    @Resource
    RedissionUtil redissionUtil;

    @GetMapping("/today")
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
        for (Order order : orders) {
            System.out.println(order);
        }
        for (var order: orders){
            var room = roomService.selectRoom(business.getId(), order.getRoomId(), true);
            var roomMap = new HashMap<String, Object>();
            roomMap.put("name", room.getName());
            roomMap.put("id", room.getId());

            var foodsMap = new ArrayList<Map<String, Object>>();

            double foodsMoney =  0.0;
            var foods = orderFoodService.getOrderFoods(order.getId());
            for (var food: foods){
                if (!food.getCancel()) foodsMoney += food.getPrice() * food.getFoodNums();

                var foodMap = new HashMap<String, Object>();
                foodMap.put("id", food.getFoodId());
                foodMap.put("name", food.getName());
                foodMap.put("count", food.getFoodNums());
                foodMap.put("price", food.getPrice());
                foodMap.put("cancel", food.getCancel());
                foodsMap.add(foodMap);
            }

            var orderMap = new HashMap<String, Object>();
            orderMap.put("id", order.getId());
            orderMap.put("room", roomMap);
            orderMap.put("foods", foodsMap);
            orderMap.put("total_price", foodsMoney);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            if(order.getFinishTime()!=null){
                orderMap.put("finish_time", sdf.format(order.getFinishTime()));
            }else{
                orderMap.put("finish_time", "0000-00-00 00:00:00");
            }
            if(order.getSubmitTime()!=null){
            orderMap.put("submit_time", sdf.format(order.getSubmitTime()));
            }else {
                orderMap.put("submit_time", "0000-00-00 00:00:00");
            }
            orderMap.put("status", order.getStatus());
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

    @GetMapping("/history")
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
            if(order.getStatus() == 2){

            }
            var room = roomService.selectRoom(business.getId(), order.getRoomId(), true);
            var roomJson = new JSONObject();
            roomJson.set("name", room.getName());
            roomJson.set("id", room.getId());

            var foodsJson = new ArrayList<JSONObject>();

            double foodsMoney =  0.0;
            var foods = orderFoodService.getOrderFoods(order.getId());
            for (var food: foods){
                if (!food.getCancel()) foodsMoney += food.getPrice() * food.getFoodNums();

                var foodJson = new JSONObject();
                foodJson.set("id", food.getFoodId());
                foodJson.set("name", food.getName());
                foodJson.set("count", food.getFoodNums());
                foodJson.set("price", food.getPrice());
                foodJson.set("cancel", food.getCancel());
                foodsJson.add(foodJson);
            }

            var orderJson = new JSONObject();
            orderJson.set("id", order.getId());
            orderJson.set("room", roomJson);
            orderJson.set("foods", foodsJson);
            orderJson.set("total_price", foodsMoney);

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            if(order.getFinishTime()!=null){
                orderJson.set("finish_time", sdf.format(order.getFinishTime()));
            }else{
                orderJson.set("finish_time", "0000-00-00 00:00:00");
            }
            if(order.getSubmitTime()!=null){
                orderJson.set("submit_time", sdf.format(order.getSubmitTime()));
            }else {
                orderJson.set("submit_time", "0000-00-00 00:00:00");
            }

            orderJson.set("status",order.getStatus());
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

    @GetMapping("/{orderID}")
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

        var orderedMap = new LinkedHashMap<Date, List<JSONObject>>();
        for(var food: orderFoods){
            var foodJson = new JSONObject();
            foodJson.set("id", food.getFoodId());
            foodJson.set("name", food.getName());
            foodJson.set("count", food.getFoodNums());
            foodJson.set("price", food.getPrice());
            foodJson.set("finish", food.getFinish());
            foodJson.set("cancel", food.getCancel());

            // 如果食物没有被取消，则计价
            if (!food.getCancel()) totalPrice += food.getPrice() * food.getFoodNums();

            var foodsBySubmitTime = orderedMap.computeIfAbsent(food.getSubmitTime(), k -> new ArrayList<>());
            foodsBySubmitTime.add(foodJson);//computeIfAbsent的返回值为map的value，然后可以通过操作这个返回值，直接改变map的value值。这里实际是在操作orderMap的value值。
//            for (JSONObject jsonObject : foodsBySubmitTime) {
//                System.out.println(jsonObject);
//            }
        }

        var suborderList = new ArrayList<HashMap<String, Object>>();
        for(var entry: orderedMap.entrySet()){
            var info = new HashMap<String, Object>();

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            info.put("submit_time", sdf.format(entry.getKey()));
            info.put("submit_id", entry.getKey().getTime());
            info.put("foods", entry.getValue());
            suborderList.add(info);
        }

        var room = roomService.selectRoom(business.getId(), order.getRoomId(), true);
        var roomJson = new JSONObject();
        roomJson.set("name", room.getName());
        roomJson.set("id", room.getId());

        var detailJson = new JSONObject();
        detailJson.set("total_price", totalPrice);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        if(order.getFinishTime()!=null){
            detailJson.set("finish_time", sdf.format(order.getFinishTime()));
        }else{
            detailJson.set("finish_time", "0000-00-00 00:00:00");
        }
        if(order.getSubmitTime()!=null){
            detailJson.set("submit_time", sdf.format(order.getSubmitTime()));
        }else {
            detailJson.set("submit_time", "0000-00-00 00:00:00");
        }
//        改了Jackson之后，这种方法更好！！！
//        detailJson.set("finish_time", order.getFinishTime());
//        detailJson.set("submit_time", order.getSubmitTime());
        detailJson.set("room", roomJson);
        detailJson.set("suborders", suborderList);
        detailJson.set("status",order.getStatus());
        return R.ok().detail(detailJson);
    }

    @PutMapping("/{orderID}/cancel/food/{foodID}")
    public R<?> cancelOrderFood(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer foodID, @PathVariable Integer orderID, @RequestBody Map<String, Object> requestJson
    ){
        var business = (Business) request.getAttribute("business");

        var order = orderService.getOrderFromID(business.getId(), orderID);
        if (order == null || order.getStatus() != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("订单错误");
        }

        Long submitID = (Long) requestJson.get("submit_id");
        if (submitID == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("submit id 错误");
        }

        var submitTime = new Date(submitID);
        var ret = orderFoodService.cancelOrderFood(order, foodID, submitTime);
        return R.ok().message("取消成功");
    }

    @PutMapping("/{orderID}/append/foods")
    public R<?> appendOrderFood(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer orderID,
            @RequestBody Map<String, List<FoodIDCount>> requestJson
    ){
        var business = (Business) request.getAttribute("business");

        var idList = new ArrayList<Integer>();
        var idCountMap = new LinkedHashMap<Integer, Integer>();
        var foodInfoList = requestJson.getOrDefault("foods", new ArrayList<>());
        for (var info: foodInfoList){
            idList.add(info.getId());
            if (idCountMap.get(info.getId()) != null){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return R.error().message("foodID重复");
            }

            idCountMap.put(info.getId(), info.getCount());
        }

        if (idList.size() == 0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("foodID错误");
        }

        var foods = foodService.getFoodsFromIDList(business.getId(), idList);
        if (foods == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("food id error");
        }

        var foodCountPairList = new ArrayList<Pair<Food, Integer>>();
        for (var food: foods){
            var foodCountPair = new Pair<>(food, idCountMap.get(food.getId()));
            foodCountPairList.add(foodCountPair);
        }

        var order = orderService.getOrderFromID(business.getId(), orderID);
        if (order == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("orderID错误");
        }

        if (order.getStatus() != 1){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("订单状态错误");
        }

        var ret = orderFoodService.appendOrderFood(order, foodCountPairList);
        System.out.println(ret);
        return R.ok().message("添加成功");
    }
// 实际上是结算订单的接口，令status为2，并存入finish_time
    @PutMapping("{orderId}/status")
    public R<?> updateStatus(@PathVariable @Min(value = 0, message = "order_id> 0") int orderId, HttpServletResponse response, HttpServletRequest request){
        Business business=(Business) request.getAttribute("business");
        Integer businessId = business.getId();

        Order order = orderService.getOrderFromID(businessId, orderId);
        if(order == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("订单状态错误");
        }
        Integer roomId = order.getRoomId();

        int update = orderService.updateOrderStatus(orderId, businessId);
        if(update == 0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("更改订单状态失败");
        }
//        System.out.println(Convert.toStr(roomId)+Convert.toStr(orderId));
//        redissionUtil.put(Convert.toStr(roomId)+Convert.toStr(orderId),"123456");
//        System.out.println((String) redissionUtil.get(Convert.toStr(roomId)+Convert.toStr(orderId)));

//        删除redis中的key和fakekey  （这两个在客户端添加的）

        redissionUtil.remove(Convert.toStr(roomId)+Convert.toStr(orderId));

//        System.out.println((String) redissionUtil.get(Convert.toStr(roomId)+Convert.toStr(orderId)));

        redissionUtil.remove(Convert.toStr(roomId)+Convert.toStr(order.getCreatorId()));

//        System.out.println((String)  redissionUtil.get(Convert.toStr(roomId)+Convert.toStr(order.getCreatorId())));
        return R.ok().message("结算成功");
    }
}
