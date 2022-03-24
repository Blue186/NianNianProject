package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Category;
import com.nian.business.mapper.CategoryMapper;
import com.nian.business.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> selectAll(QueryWrapper<Category> wrapper) {
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Category select(int id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int delete(int id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateById(category);
    }
}
