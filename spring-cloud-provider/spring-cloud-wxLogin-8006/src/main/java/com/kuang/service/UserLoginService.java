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

    UserLogin getByAccount(String account);
    //查询用户账户
    boolean queryByAccount(String loginAct);

    //查询用户账户
    boolean queryPhone(String phone);

    //验证用户密码
    boolean queryByPwd(String loginAct, String loginPwd);
}
