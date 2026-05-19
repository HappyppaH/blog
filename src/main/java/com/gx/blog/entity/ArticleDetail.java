package com.gx.blog.entity;

import lombok.Data;
import java.util.List;

@Data
public class ArticleDetail {
    private Article article;           // 文章本身
    private List<Comment> comments;    // 该文章的所有评论
}