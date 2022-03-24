package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Business;
import com.nian.business.entity.Table;
import com.nian.business.mapper.CategoryMapper;
import com.nian.business.mapper.TableMapper;
import com.nian.business.service.TableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {

    @Override
    public List<Table> selectAll(Integer businessID) {
        QueryWrapper<Table> wrapper = new QueryWrapper<>();
        wrapper.eq("business_id", businessID);
        return baseMapper.selectList(wrapper);
    }
}
