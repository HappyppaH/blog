package com.gx.blog.config;
import org.springframework.http.HttpMethod;
import com.gx.blog.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭 CSRF（前后端分离项目通常关掉）
                .csrf(csrf -> csrf.disable())

                // 无状态会话（不用 Session，全靠 JWT）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 完全放行
                        .requestMatchers("/login", "/hello", "/doc").permitAll()

                        // 注册用户放行
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        // GET 查询放行
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/articles/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()

                        // 用户管理：只有管理员能操作
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                        // 文章和评论：登录即可操作（角色不限）
                        .requestMatchers("/articles/**", "/comments/**").authenticated()

                        // 其他请求需要登录
                        .anyRequest().authenticated()
                )

                // 把我们的 JWT 过滤器加到 Spring Security 过滤器链里
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 密码加密器
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}