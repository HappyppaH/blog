package com.gx.blog.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(400, message);
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(500, "服务器内部错误：" + e.getMessage());
    }
}