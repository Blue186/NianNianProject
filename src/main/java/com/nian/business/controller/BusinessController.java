package com.nian.business.controller;

import com.nian.business.service.BusinessService;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private BusinessService businessService;

//    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }


    @GetMapping("/login")
    public R Login(){

        return R.ok().detail("hello niannian");
    }
}
