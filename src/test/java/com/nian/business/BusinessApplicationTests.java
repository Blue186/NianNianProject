package com.nian.business;

import com.nian.business.utils.QrcodeUtil;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BusinessApplicationTests {

//    @Autowired
//    @Qualifier("test")
//    private String tett;
//    @Autowired
//    @Qualifier("test3")
//    private String tetttt;

    @Resource
    QrcodeUtil qrcodeUtil;

    @Test
    void contextLoads() {
//        var data = qrcodeUtil.getRoomQrcode(1, 1);
//        qrcodeUtil.byte2image(data);
    }
}
