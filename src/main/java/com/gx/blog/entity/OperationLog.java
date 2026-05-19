package com.gx.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;      // 操作人
    private String operation;     // 操作描述
    private String method;        // 请求方法
    private String params;        // 请求参数
    private String ip;            // IP地址

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}