package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Table;
import javafx.scene.control.Tab;

import java.util.List;

public interface TableService extends IService<Table> {
    List<Table> selectAll(Integer businessID);
    Integer createTable(Table table);
    Integer deleteTable(Integer businessID, Integer tableID);
    Integer updateTable(Integer businessID, Integer tableID, String name, Integer peopleNums);
}
