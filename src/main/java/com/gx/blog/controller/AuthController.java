package com.gx.blog.controller;

import com.gx.blog.common.Result;
import com.gx.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // 1. 验证用户名密码
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. 生成 token
        String token = jwtUtil.generateToken(username);

        // 3. 返回 token
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        result.put("username", username);
        return Result.success(result);
    }
}