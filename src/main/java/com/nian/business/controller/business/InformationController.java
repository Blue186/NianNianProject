package com.nian.business.controller.business;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/business")
public class InformationController {
    @GetMapping("/information")
    public R<?> GetBusinessInformation(HttpServletRequest request, HttpServletResponse response){
        Business business = (Business) request.getAttribute("business");

        return R.ok().detail(business);
    }
}
