package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
