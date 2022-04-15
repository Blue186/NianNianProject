package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Category;
import com.nian.business.entity.vo.category.CategoryIdName;
import com.nian.business.entity.vo.category.CategoryPriorName;
import com.nian.business.mapper.CategoryMapper;
import com.nian.business.service.CategoryService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    public Category selectOne(int business_id,int category_id){
        QueryWrapper<Category> wrapper= new QueryWrapper<Category>().eq("business_id",business_id)
                                                                    .eq("id",category_id);
        Category category = baseMapper.selectOne(wrapper);
        return category;
    }

    @Override
    public List<CategoryIdName> selectAll(Integer businessId) {
        QueryWrapper<Category> wrapper= new QueryWrapper<>();
        wrapper.eq("business_id",businessId).orderByAsc("priority");
        List<Category> categories = baseMapper.selectList(wrapper);
        List<CategoryIdName> categoryIdNameList=new ArrayList<>();
        for (Category category : categories) {
            CategoryIdName categoryIdName=new CategoryIdName();
            categoryIdName.setId(category.getId());
            categoryIdName.setName(category.getName());
            categoryIdNameList.add(categoryIdName);
        }
        return categoryIdNameList;
    }

    @Override
    public int insertCategory(CategoryPriorName categoryPriorName,Integer business_id) {
        QueryWrapper<Category> nameWrapper = new QueryWrapper<Category>().eq("name", categoryPriorName.getName())
                                                                        .eq("business_id",business_id);
        Category select = baseMapper.selectOne(nameWrapper);
        if (select != null){
            return -1;
        }
        Category category = new Category();
        category.setPriority(categoryPriorName.getPriority());
        category.setName(categoryPriorName.getName());
        category.setBusinessId(business_id);
        return baseMapper.insert(category);
    }

    @Override
    public int deleteCategory(Integer categoryID) {
        return baseMapper.deleteById(categoryID);
    }

    @Override
    public int updateCategory(CategoryPriorName categoryPriorName,Integer categoryId,Integer businessId) {
        Category category = new Category();
        category.setName(categoryPriorName.getName());
        category.setPriority(categoryPriorName.getPriority());
        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessId);
        map.put("id" ,categoryId);
        return baseMapper.update(category,new QueryWrapper<Category>().allEq(map));
    }
}
