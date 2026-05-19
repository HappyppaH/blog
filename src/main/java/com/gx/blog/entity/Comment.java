package com.gx.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long articleId;       // 属于哪篇文章
    private String username;      // 评论人昵称
    private String content;       // 评论内容

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}