package com.nian.business.controller.business;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.entity.Category;
import com.nian.business.entity.vo.category.CategoryIdNameArray;
import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.category.CategoryPriorName;
import com.nian.business.service.CategoryService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        QueryWrapper<Category> wrapper= new QueryWrapper<>();
        wrapper.eq("business_id",businessId);
        List<Category> categories = categoryService.selectAll(wrapper);
        CategoryIdNameArray categoryIdNameArray =new CategoryIdNameArray();
        List<CategoryIdName> categoryIdNameList=new ArrayList<>();
        for (Category category : categories) {
            CategoryIdName categoryIdName=new CategoryIdName();
            categoryIdName.setId(category.getId());
            categoryIdName.setName(category.getName());
            categoryIdNameList.add(categoryIdName);
        }
        categoryIdNameArray.setCategories(categoryIdNameList);
        return R.ok().message("成功").detail(categoryIdNameArray);
    }

    @PostMapping
    public R<?> insertCategory(@RequestBody CategoryPriorName categoryPriorName ,HttpServletRequest request) {
        Category category = new Category();
        category.setPriority(categoryPriorName.getPriority());
        category.setName(categoryPriorName.getName());
        Business business = (Business) request.getAttribute("business");
        Integer businessId = business.getId();
        category.setBusinessId(businessId);
        int insert = categoryService.insert(category);
        if(insert==0){
            return R.error().message("插入失败");
        }
        return R.ok().message("成功");
    }

    @PutMapping("/{category_id}")
    public R<?> updateCategory(@PathVariable int category_id, @RequestBody CategoryPriorName categoryPriorName) {
        Category category = categoryService.select(category_id);
        category.setName(categoryPriorName.getName());
        category.setPriority(categoryPriorName.getPriority());
        int update = categoryService.update(category);
        if(update==0){
            return R.error().message("更改失败");
        }
        return R.ok().message("成功");
    }

    @DeleteMapping("/{category_id}")
    public R<?> deleteCategory(@PathVariable int category_id) {
        int delete = categoryService.delete(category_id);
        if(delete==0){
            return R.error().message("删除失败");
        }
        return R.ok().message("成功");
    }
}
