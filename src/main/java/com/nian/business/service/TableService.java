package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Table;

import java.util.List;

public interface TableService extends IService<Table> {
    List<Table> selectAll(Integer businessID);

}
