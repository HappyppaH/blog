package com.gx.blog.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;      // 状态码
    private String message; // 提示信息
    private T data;        // 数据

    // 私有构造方法，不让外面直接 new
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // === 静态工厂方法（外面调这些方法来创建 Result） ===

    /** 成功（带数据） */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    /** 成功（不带数据，比如新增、删除） */
    public static <T> Result<T> success() {
        return new Result<>(200, "成功", null);
    }

    /** 失败 */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /** 失败（默认错误码 500） */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
}