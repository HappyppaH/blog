package com.gx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gx.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 后，自动拥有增删改查的方法，无需自己写
}