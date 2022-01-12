package com.kuang;

import com.kuang.service.UserLoginService;
import com.kuang.springcloud.mapper.UserLoginMapper;
import com.kuang.springcloud.pojo.UserLogin;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.kuang.springcloud.mapper")
class SpringCloudWxLoginApplicationTests {
    @Autowired
    UserLoginService userLoginService;
    @Test
    void getOpenIdMemberTest() {
        UserLogin openIdMember = userLoginService.getOpenIdMember("5");
        System.out.println(openIdMember);
    }
}
