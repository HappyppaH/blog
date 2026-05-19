package com.gx.blog.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // 密钥（至少 256 位，这里用 32 个字符的字符串）
    private static final String SECRET = "MyBlogSecretKey_ABCDEFGH_1234567890_IJKLMNOP_QRSTUVWX_YZ123456";
    private static final long EXPIRE = 1000 * 60 * 60 * 24; // 24 小时

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 生成 token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)                          // 把用户名存进去
                .issuedAt(new Date())                       // 签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRE)) // 过期时间
                .signWith(getSigningKey())                  // 签名
                .compact();
    }

    // 从 token 里解析出用户名
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 验证 token 是否有效
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}