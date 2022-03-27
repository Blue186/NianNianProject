package com.nian.business.controller.business;


import cn.hutool.json.JSONObject;
import com.nian.business.entity.Business;

import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.category.CategoryPriorName;
import com.nian.business.service.CategoryService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/business/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping
    public R<?> selectCategory(HttpServletRequest request) {
        Business business = (Business) request.getAttribute("business");
        Integer businessId = business.getId();
        List<CategoryIdName> categoryIdNames = categoryService.selectAll(businessId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("categories", categoryIdNames);
        return R.ok().message("成功").detail(jsonObject);
    }

    @PostMapping
    public R<?> insertCategory(@RequestBody CategoryPriorName categoryPriorName , HttpServletRequest request, HttpServletResponse response) {
        Business business = (Business) request.getAttribute("business");
        Integer businessId = business.getId();
        int insert = categoryService.insertCategory(categoryPriorName,businessId);
        if(insert==0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("插入失败");
        }
        return R.ok().message("成功");
    }

    @PutMapping("/{category_id}")
    public R<?> updateCategory(@PathVariable int category_id, @RequestBody CategoryPriorName categoryPriorName,HttpServletResponse response,HttpServletRequest request) {
        Business business = (Business) request.getAttribute("business");
        Integer businessId = business.getId();
        int update = categoryService.updateCategory(categoryPriorName,category_id,businessId);
        if(update==0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("更改失败");
        }
        return R.ok().message("成功");
    }

    @DeleteMapping("/{category_id}")
    public R<?> deleteCategory(@PathVariable int category_id,HttpServletResponse response) {
        int delete = categoryService.deleteCategory(category_id);
        if(delete==0){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return R.error().message("删除失败");
        }
        return R.ok().message("成功");
    }
}
