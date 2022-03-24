package com.nian.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BusinessApplicationTests {

    @Autowired
    @Qualifier("test")
    private String tett;
    @Autowired
    @Qualifier("test3")
    private String tetttt;
    @Test
    void contextLoads() {
        System.out.println(tett);
        System.out.println(tetttt);
    }

}
