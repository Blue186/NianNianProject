package com.nian.business.controller.business;

import com.nian.business.entity.Business;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/business")
public class OrderController {
    @GetMapping("/order/today")
    public R<?> getTodayOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Integer count,
            @RequestParam Integer offset
    ){
        Business business = (Business) request.getAttribute("business");
        return R.ok().detail(null);
    }

    @GetMapping("/order/history")
    public R<?> getHistoryOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Integer count,
            @RequestParam Integer offset
    ){
        Business business = (Business) request.getAttribute("business");

        return R.ok().detail(null);
    }
}
