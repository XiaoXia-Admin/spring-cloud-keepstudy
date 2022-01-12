package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.springcloud.pojo.UserLogin;

/**
 * @author XiaoXia
 * @date 2021/12/29 13:09
 */

public interface UserLoginService extends IService<UserLogin> {
    //查询，根据微信
    UserLogin getOpenIdMember(String openid);

    void insertUserInfo(UserLogin member);

    UserLogin getById(String id);
}
