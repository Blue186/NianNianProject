package com.nian.business.Interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nian.business.entity.Business;
import com.nian.business.service.BusinessService;
import com.nian.business.utils.JwtUtil;
import io.fusionauth.jwt.JWTExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义拦截器
 */
@Component
public class BusinessInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final BusinessService businessService;

    //    注入bean即可使用，这里可以不写@Autowired
    @Autowired
    public BusinessInterceptor(JwtUtil jwtUtil, BusinessService businessService) {
        this.jwtUtil = jwtUtil;
        this.businessService = businessService;
    }


    /**
     * 在这里进行用户登录校验，若用户为登录，则通过response返回一个error
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenBearer = request.getHeader("Authorization");
        if(tokenBearer == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String[] tokenSplitRet = tokenBearer.split(" ");
        if (tokenSplitRet.length != 2){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        String token = tokenSplitRet[1];

        Map<String, Object> payload;
        try {
            payload = jwtUtil.decodeToken(token);
        }catch (JWTExpiredException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        QueryWrapper<Business> wrapper = new QueryWrapper<>();
        wrapper.eq("id", payload.get("uid"));
        Business business = businessService.getBaseMapper().selectOne(wrapper);
        if (business == null){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        request.setAttribute("business", business);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
