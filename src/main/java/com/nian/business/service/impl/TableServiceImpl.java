package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Table;
import com.nian.business.mapper.TableMapper;
import com.nian.business.service.TableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {

    @Override
    public List<Table> selectAll(Integer businessID) {
        return baseMapper.selectList(new QueryWrapper<Table>().eq("business_id", businessID));
    }

    @Override
    public Integer createTable(Table table) {
        return baseMapper.insert(table);
    }

    @Override
    public Integer deleteTable(Integer businessID, Integer tableID) {
        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessID);
        map.put("id" , tableID);

        return baseMapper.delete(new QueryWrapper<Table>().allEq(map));
    }

    @Override
    public Integer updateTable(Integer businessID, Integer tableID, String name, Integer peopleNums) {
        Table table = new Table();
        table.setName(name);
        table.setPeopleNums(peopleNums);

        Map<String , Object> map = new HashMap<>();
        map.put("business_id" , businessID);
        map.put("id" , tableID);

        return baseMapper.update(table, new QueryWrapper<Table>().allEq(map));
    }
}
