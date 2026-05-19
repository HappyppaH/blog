package com.gx.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.blog.common.Result;
import com.gx.blog.entity.Article;
import com.gx.blog.entity.ArticleDetail;
import com.gx.blog.entity.Comment;
import com.gx.blog.service.ArticleService;
import com.gx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gx.blog.annotation.Log;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.gx.blog.entity.User;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    // 查询所有文章（保留，快速查看用）
    @GetMapping("/all")
    public Result<List<Article>> listAll() {
        return Result.success(articleService.listAll());
    }

    // 🆕 分页 + 条件查询（主查询接口）
    @GetMapping
    public Result<Page<Article>> search(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword
    ) {
        Page<Article> result = articleService.search(page, size, category, keyword);
        return Result.success(result);
    }

    // 查询单篇文章
    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    // 新增文章
    @Log("发布文章")
    @PostMapping
    public Result<String> add(@RequestBody Article article) {
        // 自动获取当前登录用户的 ID
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        article.setAuthorId(currentUser.getId());

        articleService.add(article);
        return Result.success("文章发布成功！");
    }

    // 修改文章
    @Log("修改文章")
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable Long id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);
        return Result.success("文章修改成功！");
    }

    // 删除文章
    @Log("删除文章")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return Result.success("文章删除成功！");
    }
    // 🆕 文章详情（带评论）
    @GetMapping("/{id}/detail")
    public Result<ArticleDetail> getDetail(@PathVariable Long id) {
        Article article = articleService.getById(id);
        List<Comment> comments = commentService.listByArticleId(id);

        ArticleDetail detail = new ArticleDetail();
        detail.setArticle(article);
        detail.setComments(comments);

        return Result.success(detail);
    }
}