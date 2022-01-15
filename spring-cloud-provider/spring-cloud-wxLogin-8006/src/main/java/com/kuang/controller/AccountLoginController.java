package com.kuang.controller;

import com.kuang.service.UserLoginService;
import com.kuang.springcloud.pojo.UserLogin;
import com.kuang.springcloud.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoXia
 * @date 2022/01/13 12:18
 */

@RestController
@RequestMapping("/api/ulogin/account")
@CrossOrigin
public class AccountLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> accountLogin(String loginAct, String loginPwd) {
        //将密码的明文形式转换为MD5的密文形式
//        loginPwd = MD5Util.getMD5(loginPwd);
        boolean account = true;
        Map<String, Object> map;
        if (loginAct.length() == 11) {
            account = userLoginService.queryPhone(loginAct);
        } else {
            account = userLoginService.queryByAccount(loginAct);
        }
        boolean verify = userLoginService.queryByPwd(loginAct, loginPwd);
        map = new HashMap<String, Object>();
        map.put("account", account);
        map.put("verify", verify);
        return map;
    }

    @PostMapping("/userInfo")
    @ResponseBody
    public R getAccountUserInfo(String account) {
        System.out.println(account);
        UserLogin member = userLoginService.getByAccount(account);
        System.out.println(member);
        return R.ok().data("userInfo",member);
    }
}
