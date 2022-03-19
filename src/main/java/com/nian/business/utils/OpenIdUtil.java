package com.nian.business.utils;



import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 处理获取用户 openid 的 util
 */
@Slf4j
@Component
public class OpenIdUtil {

    private String secret = "44f414d6c5b0f92e998d99b32c2fb2b1";
    private String appid = "wx9e563fb3b3e19d91";

    public  String getOpenid(String code) {
        log.info("code:{}",code);

        JSONObject codeJson = new JSONObject(code);
        String s = codeJson.get("code").toString();

        BufferedReader in = null;
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="
                +appid+"&secret="+secret+"&js_code="+s+"&grant_type=authorization_code";
        try {
            URL weChatUrl = new URL(url);
        // 打开和URL之间的连接
            URLConnection connection = weChatUrl.openConnection();
        // 设置通用的请求属性
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
        // 建立实际的连接
            connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            Object openid = jsonObject.get("openid");
            return openid.toString();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


}
