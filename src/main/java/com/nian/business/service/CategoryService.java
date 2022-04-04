package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Category;
import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.category.CategoryPriorName;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryIdName> selectAll(Integer businessId);
    int insertCategory(CategoryPriorName categoryPriorName,Integer business_id);
    int deleteCategory(Integer categoryID);
    int updateCategory(CategoryPriorName categoryPriorName,Integer categoryId,Integer businessId);
    Category selectOne(int business_id,int category_id);
}
