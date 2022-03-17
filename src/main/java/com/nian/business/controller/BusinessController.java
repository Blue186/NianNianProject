package com.nian.business.controller;

import com.nian.business.service.BusinessService;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/business/")
public class BusinessController {

    private BusinessService businessService;

//    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }


//    @PostMapping("/login")
//    public R Login(@RequestBody String code){
//        .....
//        return R.ok().data();
//    }
}
