package com.nian.business.controller.visitor;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.service.BusinessService;
import com.nian.business.utils.JwtUtil;
import com.nian.business.utils.OpenIdUtil;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/visitor")
public class LoginController {

    private final BusinessService businessService;
    private final OpenIdUtil openIdUtil;
    private final JwtUtil jwtUtil;

//    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public LoginController(
            BusinessService businessService,
            OpenIdUtil openIdUtil,
            JwtUtil jwtUtil
    ) {
        this.businessService = businessService;
        this.openIdUtil = openIdUtil;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login/wechat")
    public R<?> Login(@RequestBody String code, HttpServletResponse response){
//        String openid = openIdUtil.getOpenid(code);
        String openid = "asd";
        if(openid == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
