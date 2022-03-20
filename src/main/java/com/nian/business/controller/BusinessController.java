package com.nian.business.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.service.BusinessService;
import com.nian.business.utils.JwtUtil;
import com.nian.business.utils.OpenIdUtil;
import com.nian.business.utils.R;
import javafx.beans.value.ObservableObjectValue;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;
    private final OpenIdUtil openIdUtil;
    private final JwtUtil jwtUtil;

//    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public BusinessController(
            BusinessService businessService,
            OpenIdUtil openIdUtil,
            JwtUtil jwtUtil
    ) {
        this.businessService = businessService;
        this.openIdUtil = openIdUtil;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public R<?> Login(@RequestBody String code){
        String openid = openIdUtil.getOpenid(code);
        if(openid == null){
            return R.error().message("获取openid失败");
        }

        QueryWrapper<Business> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        Business business = businessService.getBaseMapper().selectOne(wrapper);

        if (business == null){
            business = new Business();
            business.setOpenid(openid);
            int insert = businessService.getBaseMapper().insert(business);
            if (insert != 1){
                return R.error().message("未注册，注册失败");
            }
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("uid", business.getId());

        String token = jwtUtil.encodeToken(payload);

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("token", token);

        return R.ok().detail(jsonObject);
    }
}
