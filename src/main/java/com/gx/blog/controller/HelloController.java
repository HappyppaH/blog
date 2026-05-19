package com.gx.blog.controller;

import com.gx.blog.common.Result;
import com.gx.blog.entity.User;
import com.gx.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // 🆕 统一给所有方法加 /users 前缀
public class HelloController {

    @Autowired
    private UserService userService;

    // 查询所有用户  GET /users
    @GetMapping
    public Result<List<User>> listUsers() {
        List<User> users = userService.listAllUsers();
        return Result.success(users);
    }

    // 查询单个用户  GET /users/1
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    // 新增用户  POST /users
    @PostMapping
    public Result<String> addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return Result.success("用户添加成功！");
    }

    // 修改用户  PUT /users/1
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);  // 确保 ID 一致，防止被篡改
        userService.updateUser(user);
        return Result.success("用户修改成功！");
    }

    // 删除用户  DELETE /users/1
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("用户删除成功！");
    }
}