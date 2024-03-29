package com.nian.business.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *上传图片
 * 前端传递Base64编码到后端，String
 * 后端根据Base64进行解码操作，拿到文件后缀名。
 * 转存文件到本地的目录
 * 将目录信息+文件名保存到数据库==图片的地址
 * 将地址返回给前端。
 * data:image/png;base64,iVBORw0KGgoAAA
 * ANSUhEUgAAB1EAAAGdCAYAAABD6YrOAAAAAXNSR0IArs4c
 * 6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAA
 * P4dSURBVHhe7N0NYBTVvT7+J0iiN0UEJRDejYZyS8r9BavUECqSe4lyoV
 *IiIiIKnrBT3
 */

@Component
public class FileUtil {
    @Value("${server.port}")
    private String port;
    private static final String ip = "http://139.186.170.118";

    public String uploadImage(String base64Str){
        //            这里要先截取一下图片原编码和拿到图片后缀
        String prefix = "";
        String data = "";//实体部分数据
        if(base64Str==null||"".equals(base64Str)){
            return "0";//上传失败，上传图片数据为空
        }else {
            String [] d = base64Str.split("base64,");//将字符串分成数组
            if(d != null && d.length == 2){
                prefix = d[0];
                data = d[1];
            }else {
                return "-1";//上传失败，数据不合法
            }
        }
        String suffix = "";//图片后缀，用以识别哪种格式数据
        //data:image/jpeg;base64,base64编码的jpeg图片数据
        if("data:image/jpeg;".equalsIgnoreCase(prefix)){//JPG
            suffix = ".jpg";
        }else if("data:image/x-icon;".equalsIgnoreCase(prefix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if("data:image/gif;".equalsIgnoreCase(prefix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if("data:image/png;".equalsIgnoreCase(prefix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            return "-2";//上传图片格式不合法,支持jpg,ico,png,gif
        }

        String flag = IdUtil.fastSimpleUUID();//生成随记UUID码:ajspdjasdkjasasdasjdla
        String imageName = flag+suffix;
//        String imageFilePath = System.getProperty("user.dir")+"/torch_server/app_server/src/main/resources/static/images/"+imageName;//本地使用
        String imageFilePath = System.getProperty("user.dir")+"/static/images/"+imageName;//服务器使用
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = base64Decoder.decodeBuffer(data);//转成2进制
            OutputStream out = new FileOutputStream(imageFilePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip+":"+port+"/static/images/" +imageName;
    }

}
