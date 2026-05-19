package com.gx.blog.service;

import com.gx.blog.entity.User;
import java.util.List;

public interface UserService {
    List<User> listAllUsers();           // 查全部   ✅ 已有
    User getUserById(Long id);           // 查单个   🆕
    void addUser(User user);             // 新增     ✅ 已有
    void updateUser(User user);          // 修改     🆕
    void deleteUser(Long id);            // 删除     🆕
}