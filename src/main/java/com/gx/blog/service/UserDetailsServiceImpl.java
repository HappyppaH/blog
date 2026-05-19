package com.gx.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.blog.entity.User;
import com.gx.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在：" + username);
        }
        return user;
    }
}