package com.gx.blog.aspect;

import com.gx.blog.annotation.Log;
import com.gx.blog.entity.OperationLog;
import com.gx.blog.mapper.OperationLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@Aspect      // 告诉 Spring：这是个切面类
@Component   // 交给 Spring 管理
public class LogAspect {

    @Autowired
    private OperationLogMapper logMapper;

    // @Around("@annotation(log)") 意思是：拦截所有带了 @Log 注解的方法
    @Around("@annotation(log)")
    public Object around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        // ===== 方法执行前 =====
        long startTime = System.currentTimeMillis();

        // 执行原方法
        Object result = joinPoint.proceed();

        // ===== 方法执行后 =====
        long timeCost = System.currentTimeMillis() - startTime;

        // 保存日志
        saveLog(joinPoint, log, timeCost);

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Log log, long timeCost) {
        OperationLog operationLog = new OperationLog();

        // 操作描述（来自 @Log 注解的值）
        operationLog.setOperation(log.value());

        // 请求方法名（类名.方法名）
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operationLog.setMethod(className + "." + methodName);

        // 请求参数
        Object[] args = joinPoint.getArgs();
        operationLog.setParams(Arrays.toString(args));

        // 操作人（暂时写 "admin"，Day 11 集成登录后改成真实用户名）
        // 从 Spring Security 上下文获取当前登录用户的用户名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            operationLog.setUsername(auth.getName());
        } else {
            operationLog.setUsername("anonymous");
        }

        // IP 地址
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            operationLog.setIp(request.getRemoteAddr());
        }

        // 插入数据库
        logMapper.insert(operationLog);
    }
}