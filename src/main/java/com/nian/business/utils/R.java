package com.nian.business.utils;

import com.nian.business.utils.inter.ResultCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class R<T> {

    private String status;

    private String message;

    private T detail;

    private Integer code;//个人觉得应该还有设置一个code错误码

    public R() {
    }

    public static <T> R<T> ok(){
        R<T> r = new R<>();
        r.setStatus(ResultCode.SUCCESS);
//        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.setStatus(ResultCode.ERROR);
//        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R<T> status(String status) {
        this.setStatus(status);
        return this;
    }

    public R<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setDetail(T data) {
        this.detail = data;
    }

    public R<T> detail(T data) {
        this.setDetail(data);
        return this;
    }

    public R<T> setReLoginData() {
        Map<String, Integer> resultMap = new HashMap<>();
//        resultMap.put("code", -100);
        this.detail((T) resultMap);
        return this;
    }

    public R<T> setErrorCode(Integer errorCode) {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("errorCode", errorCode);
        this.detail((T) resultMap);
        return this;
    }
}
