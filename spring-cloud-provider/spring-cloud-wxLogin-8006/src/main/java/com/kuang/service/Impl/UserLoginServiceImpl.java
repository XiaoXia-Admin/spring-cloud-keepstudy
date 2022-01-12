package com.kuang.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.service.UserLoginService;
import com.kuang.springcloud.exceptionhandler.XiaoXiaException;
import com.kuang.springcloud.mapper.UserLoginMapper;
import com.kuang.springcloud.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author XiaoXia
 * @date 2021/12/28 20:31
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements UserLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;

    /**
     *  通过openid查询用户信息
     * @param openid 用户openID
     * @return 查询到的用户，没有返回null
     */
    @Override
    public UserLogin getOpenIdMember(String openid) {
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("openid" , openid);
        UserLogin member = userLoginMapper.selectOne(wrapper);
        if(member != null){
            if(member.getIsDisabled()){
                throw new XiaoXiaException(20001 , "失败");
            }
        }
        return member;
    }

    /**
     *  插入一个用户的信息
     * @param member 登录的用户实体
     */
    @Override
    public void insertUserInfo(UserLogin member) {
        int insert = baseMapper.insert(member);
        System.out.println(insert);
        if(insert != 1){
            throw new XiaoXiaException(20001 , "插入失败");
        }
    }

    @Override
    public UserLogin getById(String id) {
        UserLogin userLogin = userLoginMapper.selectById(id);
        return userLogin;
    }
}
