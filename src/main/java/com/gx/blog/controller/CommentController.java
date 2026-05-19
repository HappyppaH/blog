package com.gx.blog.controller;

import com.gx.blog.common.Result;
import com.gx.blog.entity.Comment;
import com.gx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gx.blog.annotation.Log;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 查询某篇文章的所有评论  GET /comments?articleId=1
    @GetMapping
    public Result<List<Comment>> listByArticle(@RequestParam Long articleId) {
        return Result.success(commentService.listByArticleId(articleId));
    }

    // 新增评论
    @Log("发表评论")
    @PostMapping
    public Result<String> add(@RequestBody Comment comment) {
        commentService.add(comment);
        return Result.success("评论发表成功！");
    }

    // 删除评论
    @Log("删除评论")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success("评论删除成功！");
    }
}