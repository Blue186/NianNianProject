package com.nian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nian.business.entity.OrderConsumer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface OrderConsumerMapper extends BaseMapper<OrderConsumer> {
}
