package com.kuang.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.springcloud.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author XiaoXia
 * @date 2021/12/29 13:15
 */
@Repository
public interface UserLoginMapper extends BaseMapper<UserLogin> {
}
