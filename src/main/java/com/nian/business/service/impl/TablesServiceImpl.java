package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Tables;
import com.nian.business.mapper.TablesMapper;
import com.nian.business.service.TablesService;
import org.springframework.stereotype.Service;

@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, Tables> implements TablesService {
}
