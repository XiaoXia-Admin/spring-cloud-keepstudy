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
        if(insert != 1){
            throw new XiaoXiaException(20001 , "插入失败");
        }
    }

    /**
     * 根据id查询用户
     * @param id
     * @return 用户实体信息
     */
    @Override
    public UserLogin getById(String id) {
        UserLogin userLogin = userLoginMapper.selectById(id);
        return userLogin;
    }

    /**
     * 根据用户账号进行验证
     * @param loginAct
     * @return true为存在，false为不存在用户
     */
    @Override
    public boolean queryByAccount(String loginAct) {
        boolean flag = true;
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("account" , loginAct);
        UserLogin member = userLoginMapper.selectOne(wrapper);
        if(member != null){
            if(member.getIsDisabled()){
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据用户账号密码进行验证
     * @param loginAct 账号
     * @param loginPwd 密码
     * @return true正确，false错误
     */
    @Override
    public boolean queryByPwd(String loginAct, String loginPwd) {
        boolean flag = true;
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("account" , loginAct);
        wrapper.eq("password" , loginPwd);
        UserLogin member = userLoginMapper.selectOne(wrapper);
        if(member != null){
            if(member.getIsDisabled()){
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据电话号进行验证
     * @param phone 手机号
     * @return true为存在，false为不存在
     */
    @Override
    public boolean queryPhone(String phone) {
        boolean flag = true;
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile" , phone);
        UserLogin member = userLoginMapper.selectOne(wrapper);
        if(member != null){
            if(member.getIsDisabled()){
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据用户账号获取用户信息
     * @param account 用户账号
     * @return 返回用户信息
     */
    @Override
    public UserLogin getByAccount(String account) {
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("account" , account);
        UserLogin member = userLoginMapper.selectOne(wrapper);
        return member;
    }
}
