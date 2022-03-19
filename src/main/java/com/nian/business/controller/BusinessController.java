package com.nian.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.service.BusinessService;
import com.nian.business.utils.OpenIdUtil;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private BusinessService businessService;
    private OpenIdUtil openIdUtil;

//    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public BusinessController(BusinessService businessService,
                              OpenIdUtil openIdUtil) {
        this.businessService = businessService;
        this.openIdUtil = openIdUtil;
    }


    @PostMapping("/login")
    public R<?> Login(@RequestBody String code){
        String openid = openIdUtil.getOpenid(code);
        QueryWrapper<Business> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Business business = businessService.getBaseMapper().selectOne(wrapper);
        if (business==null){
            Business newBusiness = new Business();
            newBusiness.setOpenid(openid);
            int insert = businessService.getBaseMapper().insert(newBusiness);
            if (insert==1){
                return R.ok().detail(newBusiness);
            }
            return R.error().message("未注册，注册失败");
        }else {
            return R.ok().detail(business);
        }
    }
}
