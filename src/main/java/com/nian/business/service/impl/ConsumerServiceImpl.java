package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Consumer;
import com.nian.business.mapper.ConsumerMapper;
import com.nian.business.service.ConsumerService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper,Consumer> implements ConsumerService{

}
