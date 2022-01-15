package com.kuang.service.Impl;

import com.kuang.service.UserLoginService;
import com.kuang.springcloud.mapper.UserLoginMapper;
import com.kuang.springcloud.pojo.UserLogin;
import com.kuang.springcloud.utils.JwtUtils;
import com.kuang.springcloud.utils.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author XiaoXia
 * @date 2021/12/29 13:26
 */
@SpringBootTest
@MapperScan("com.kuang.springcloud.mapper")
class UserLoginServiceImplTest {
    @Autowired
    UserLoginService userLoginService;

    @Autowired
    UserLoginMapper userLoginMapper;

    /**
     * 通过openid获取用户信息
     */
    @Test
    void getOpenIdMemberTest() {
        UserLogin openIdMember = userLoginService.getOpenIdMember("1");
        System.out.println(openIdMember);
    }

    /**
     * 插入信息
     */
    @Test
    void insertUserInfoTest() {
        UserLogin member = new UserLogin();
        member.setOpenid("7");
        member.setNickname("大爱无疆");
        member.setAvatar("touxiang7");
        member.setSex(1);
        userLoginService.insertUserInfo(member);
    }

    /**
     * 通过用户id查询微信的登录信息
     */
    @Test
    void getUserInfoById() {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("ukc8BDbRigUDaY6pZFfWus2jZWLPHO").parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2NDA4NTI2ODUsImV4cCI6MTY0MDkzOTA4NSwiaWQiOiIxNDc2NDY5MjkxNTk0MzI2MDE4Iiwibmlja25hbWUiOiLlpI_ph5HlrocifQ.oOrP1BEh57KxlbuJNQ-Gs5O5W1ZKETj4_FS9DB-AJdE");
        Claims claims = claimsJws.getBody();
        String id = (String) claims.get("id");
        UserLogin member = userLoginService.getById(id);
        System.out.println(member);
    }

    /**
     * 根据用户账号进行验证
     */
    @Test
    void queryByAccount() {
        boolean flag = userLoginService.queryByAccount("14764795");
        System.out.println(flag);
    }

    /**
     * 根据用户账号进行验证
     */
    @Test
    void queryByPhone() {
        boolean flag = userLoginService.queryPhone("18345185287");
        System.out.println(flag);
    }

    /**
     * 根据用户账号密码进行验证
     */
    @Test
    void queryByPwd() {
        boolean flag = userLoginService.queryByPwd("14764794", "138450");
        System.out.println(flag);
    }

    /**
     * 根据用户账号查询用户信息
     */
    @Test
    void getByAccount() {
        UserLogin byAccount = userLoginService.getByAccount("14764794");
        System.out.println(byAccount);
    }
}