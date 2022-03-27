package com.nian.business.controller.business;

import com.nian.business.entity.Business;
import com.nian.business.entity.vo.food.FoodNoId;
import com.nian.business.entity.vo.food.FoodNoStatus;
import com.nian.business.entity.vo.food.FoodStatus;
import com.nian.business.service.FoodService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/business/food")
public class FoodController {
    @Resource
    private FoodService foodService;
    @PostMapping
    public R<?> insertFood(@RequestBody FoodNoId foodNoId ,HttpServletRequest request,HttpServletResponse response){
        Business business=(Business) request.getAttribute("business");
        int insert = foodService.insertFood(foodNoId,business.getId());
        if (insert==0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("插入失败");
        }
        return R.ok().message("成功");
    }
    @PutMapping("/{food_id}")
    public R<?> updateFood(@PathVariable int food_id, @RequestBody FoodNoStatus foodNoStatus,HttpServletResponse response,HttpServletRequest request){
        Business business=(Business) request.getAttribute("business");
        int update = foodService.updateFoodNoStatus(foodNoStatus,food_id,business.getId());
        if(update==0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("编辑菜品失败");
        }
        return R.ok().message("成功");
    }
    @PutMapping("/{food_id}/status")
    public R<?> updateStatus(@PathVariable int food_id, @RequestBody FoodStatus foodstatus, HttpServletResponse response,HttpServletRequest request){
        Business business=(Business) request.getAttribute("business");
        int update = foodService.updateFoodStatus(foodstatus,food_id,business.getId());
        if(update == 0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("更改上下架状态失败");
        }
        return R.ok().message("成功");
    }
}
