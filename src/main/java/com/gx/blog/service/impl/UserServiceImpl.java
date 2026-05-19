package com.gx.blog.service.impl;

import com.gx.blog.entity.User;
import com.gx.blog.mapper.UserMapper;
import com.gx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 加密密码
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
}