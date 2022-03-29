package com.nian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.business.entity.Business;
import com.nian.business.entity.vo.business.BusinessInformation;

public interface BusinessService extends IService<Business> {
//   service层 这里可以对数据进行处理，封装一些方法，通过子类实现
    boolean updateInformation(Integer businessID, BusinessInformation information);
}
