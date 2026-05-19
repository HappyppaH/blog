package com.gx.blog.service;

import com.gx.blog.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> listByArticleId(Long articleId);  // 查某篇文章的所有评论
    void add(Comment comment);                       // 新增评论
    void delete(Long id);                            // 删除评论
}