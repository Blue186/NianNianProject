package com.nian.business.controller.business;

import com.nian.business.entity.Category;
import com.nian.business.entity.vo.CategoryIdNameArray;
import com.nian.business.entity.vo.CategoryIdName;
import com.nian.business.entity.vo.CategoryPriorName;
import com.nian.business.service.CategoryService;
import com.nian.business.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/business/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping
    public R<?> selectCategory() {
        List<Category> categories = categoryService.selectAll();
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
    public R<?> insertCategory(@RequestBody CategoryPriorName categoryPriorName) {
        Category category = new Category();
        category.setPriority(categoryPriorName.getPriority());
        category.setName(categoryPriorName.getName());
        categoryService.insert(category);
        return R.ok().message("成功");
    }

    @PutMapping("/{category_id}")
    public R<?> updateCategory(@PathVariable int category_id, @RequestBody CategoryPriorName categoryPriorName) {
        Category category = categoryService.select(category_id);
        category.setName(categoryPriorName.getName());
        category.setPriority(categoryPriorName.getPriority());
        categoryService.update(category);
        return R.ok().message("成功");
    }

    @DeleteMapping("/{category_id}")
    public R<?> deleteCategory(@PathVariable int category_id) {
        categoryService.delete(category_id);
        return R.ok().message("成功");
    }
}
