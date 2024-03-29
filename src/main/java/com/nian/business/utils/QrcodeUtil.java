package com.nian.business.utils;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * 生成桌面二维码
 */
@Slf4j
@Component
public class QrcodeUtil {
    @Value("${wechat.qrcode.appid}")
    private String appid;
    @Value("${wechat.qrcode.secret}")
    private String secret;

    @Value("${wechat.qrcode.page}")
    private String qrcodePage;
    @Value("${wechat.qrcode.check-path}")
    private boolean qrcodeCheckPath;
    @Value("${wechat.qrcode.env-version}")
    private String qrcodeEnvVersion;
    @Value("${wechat.qrcode.is-hyaline}")
    private boolean qrcodeIsHyaline;
    @Value("${wechat.qrcode.width}")
    private Integer qrcodeWidth;
    @Value("${wechat.qrcode.save-path}")
    private String savePath;

    private String accessToken;
    private final RestTemplate restTemplate = new RestTemplate();
//    获取access_token
    private String getAccessToken(boolean refresh){
        if (accessToken == null || refresh){
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type={grant_type}&appid={appid}&secret={secret}";
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "client_credential");
            params.put("appid", appid);
            params.put("secret", secret);

            var respMap = restTemplate.getForObject(url, Map.class, params);
            if (respMap == null){
                return "";
            }
            accessToken = (String) respMap.get("access_token");
        }

        return accessToken;
    }
//byte2image：
//1. 将byte[ ] 写入指定的保存目录下。
    public String byte2image(byte[] data){
        if(data == null || data.length<3 || savePath.equals("")) {
            log.error("save-path is null");
            return null;
        }

        var filename = UUID.randomUUID().toString().replace("-", "").toLowerCase() + ".png";
        var filepath = String.format("%s/%s", savePath, filename);
        log.debug(String.format("generate file %s", filepath));

        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(filepath));//打开输入流
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            return filename;
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }

        return null;
    }
//根据access_token ,以及自身一些需求,生成二维码，并存在byte[]中
    public byte[] getRoomQrcode(Integer roomID, Integer businessID, Boolean refresh){
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token={access_token}";
//        57_R2nppOGzHnlcCdCWt6d_EW8LQfHdGpdPwLku3Fb9az0YUtCD7UJUUrkv8Lf-JaoJ0dFaUr9usvtB35dl9R2_riiIm65-4z9NVTrrFfSWn7XdldzY_FY0hvEuOYcl4kdyT-V0D0qA_b6_cx77DOWeAJAXIQ
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("access_token", getAccessToken(refresh));

        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("scene", String.format("room_id=%s&business_id=%s", roomID, businessID));
        bodyParams.put("page", qrcodePage);
        bodyParams.put("check_path", qrcodeCheckPath);
        bodyParams.put("env_version", qrcodeEnvVersion);
        bodyParams.put("is_hyaline", qrcodeIsHyaline);
        bodyParams.put("width", qrcodeWidth);

        var response = restTemplate.postForEntity(url, bodyParams, Resource.class, urlParams);
        var responseBody = response.getBody();
        var contentType = Objects.requireNonNull(response.getHeaders().get("Content-Type")).get(0);
        if (responseBody == null){
            return null;
        }

        if (contentType.startsWith("image")){
            InputStream inputStream;
            try {
                inputStream = responseBody.getInputStream();
                byte[] buf = new byte[inputStream.available()];
                int count = 0;
                int n;
                while (true) {
                    n = inputStream.read();
                    if (n == -1){
                        break;
                    }

                    buf[count++] = (byte) n;
                }

                return buf;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            InputStream inputStream;
            try {
                StringBuilder resultString = new StringBuilder();
                inputStream = responseBody.getInputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) != -1) {
                    resultString.append(new String(buf, 0, len, Charset.defaultCharset()));
                }
                var responseJson = new JSONObject(resultString);
                log.error(String.valueOf(responseJson));
                log.error(contentType);
                var errorCode = (Integer) responseJson.get("errcode");

                if (errorCode == 42001 && !refresh){
                    log.error("refresh token");
                    // 递归获取刷新结果
                    return this.getRoomQrcode(roomID, businessID, true);
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
