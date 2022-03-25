package com.nian.business.controller.business;

import com.nian.business.entity.Business;
import com.nian.business.entity.Food;
import com.nian.business.entity.vo.food.FoodNoId;
import com.nian.business.entity.vo.food.FoodNoStatus;
import com.nian.business.entity.vo.food.Status;
import com.nian.business.service.FoodService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/business/food")
public class FoodController {
    @Resource
    private FoodService foodService;
    @PostMapping
    public R<?> insertFood(@RequestBody FoodNoId foodNoId ,HttpServletRequest request){
        Business business=(Business) request.getAttribute("business");

        Food food=new Food();
        food.setName(foodNoId.getName());
        food.setImage(foodNoId.getImage());
        food.setIntroduce(foodNoId.getIntroduce());
        food.setCategoryId(foodNoId.getCategoryId());
        food.setPrice(foodNoId.getPrice());
        food.setStatus(foodNoId.getStatus());
        food.setBusinessId(business.getId());

        int insert = foodService.insert(food);
        if (insert==0){
            return R.error().message("插入失败");
        }
        return R.ok().message("成功");
    }

    @PutMapping("/{food_id}")
    public R<?> updateFood(@PathVariable int food_id, @RequestBody FoodNoStatus foodNoStatus){

        Food food = foodService.select(food_id);
        food.setPrice(foodNoStatus.getPrice());
        food.setImage(foodNoStatus.getImage());
        food.setIntroduce(foodNoStatus.getIntroduce());
        food.setName(foodNoStatus.getName());
        food.setCategoryId(foodNoStatus.getCategoryId());
        int update = foodService.update(food);
        if(update==0){
            return R.error().message("编辑菜品失败");
        }

        return R.ok().message("成功");
    }

    @PutMapping("/{food_id}/status")
    public R<?> updateStatus(@PathVariable int food_id, @RequestBody Status status){
        Food food = foodService.select(food_id);
        food.setStatus(status.getStatus());
        int update = foodService.update(food);
        if(update == 0){
            return R.error().message("更改上下架状态失败");
        }
        return R.ok().message("成功");
    }
}
