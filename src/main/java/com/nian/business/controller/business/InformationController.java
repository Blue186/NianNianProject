package com.nian.business.controller.business;

import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;
import com.nian.business.service.OrderFoodService;
import com.nian.business.service.OrderService;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class InformationController {
    @Resource
    OrderService orderService;
    @Resource
    OrderFoodService orderFoodService;

    @GetMapping("/information")
    public R<?> GetBusinessInformation(HttpServletRequest request, HttpServletResponse response){
        Business business = (Business) request.getAttribute("business");

        double money = 0.0, pendingMoney = 0.0;
        int orderNums = 0, pendingOrderNums = 0;

        var orders = orderService.getTodayOrder(business.getId());
        for (var order: orders) {
            var orderFoods = orderFoodService.getOrderFoods(order.getId());
            switch (order.getStatus()){
                case 1:
                    pendingOrderNums++;
                    for (var food: orderFoods){
                        pendingMoney += food.getPrice() * food.getFoodNums();
                    }
                    break;
                case 2:
                    orderNums++;
                    for (var food: orderFoods){
                        money += food.getPrice() * food.getFoodNums();
                    }
                    break;
            }
        }

        // 封装商户信息
        JSONObject businessJson  = new JSONObject();
        businessJson.set("shop_name", business.getShopName());
        businessJson.set("address", business.getAddress());
        businessJson.set("image", business.getImage());

        // 封装统计信息
        JSONObject statisticsJson = new JSONObject();
        statisticsJson.set("money", money);
        statisticsJson.set("order_nums", orderNums);
        statisticsJson.set("pending_money", pendingMoney);
        statisticsJson.set("pending_order_nums", pendingOrderNums);

        JSONObject detailJson = new JSONObject();
        detailJson.set("business", businessJson);
        detailJson.set("statistics", statisticsJson);

        return R.ok().detail(detailJson);
    }
}
