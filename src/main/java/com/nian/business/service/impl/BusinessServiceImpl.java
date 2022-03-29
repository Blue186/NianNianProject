package com.nian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.business.entity.Business;
import com.nian.business.entity.vo.business.BusinessInformation;
import com.nian.business.mapper.BusinessMapper;
import com.nian.business.service.BusinessService;
import lombok.var;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
    @Override
    public boolean updateInformation(Integer businessID, BusinessInformation information) {
        var updatedBusiness = new Business();
        updatedBusiness.setShopName(information.getShopName());
        updatedBusiness.setBossName(information.getBossName());
        updatedBusiness.setAddress(information.getAddress());
        updatedBusiness.setImage(information.getImage());
        updatedBusiness.setPhone(information.getPhone());

        var ret = baseMapper.update(updatedBusiness, new QueryWrapper<Business>().eq("id", businessID));
        return ret == 1;
    }
}
