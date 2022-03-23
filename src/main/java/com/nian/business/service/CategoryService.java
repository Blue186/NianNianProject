package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> selectAll();
    Category select(int id);
    int insert(Category category);
    int delete(int id);
    int update(Category category);

}
