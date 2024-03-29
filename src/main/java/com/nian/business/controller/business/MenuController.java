package com.nian.business.controller.business;

import com.nian.business.entity.Business;
import com.nian.business.service.CategoryService;
import com.nian.business.service.FoodService;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class MenuController {
    @Resource
    CategoryService categoryService;
    @Resource
    FoodService foodService;

    @GetMapping("/menu")
    public R<?> getMenu(HttpServletRequest request, HttpServletResponse response){
        var business = (Business) request.getAttribute("business");

        var categories = categoryService.selectAll(business.getId());
        var foods = foodService.selectAll(business.getId());

        var jsonMap = new HashMap<String, Object>();
        jsonMap.put("categories", categories);
        jsonMap.put("foods", foods);

        return R.ok().detail(jsonMap);
    }
}
