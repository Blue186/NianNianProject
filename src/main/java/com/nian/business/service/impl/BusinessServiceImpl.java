package com.nian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Business;
import com.nian.business.mapper.BusinessMapper;
import com.nian.business.service.BusinessService;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
//    这里实现继承接口的方法即可
}
