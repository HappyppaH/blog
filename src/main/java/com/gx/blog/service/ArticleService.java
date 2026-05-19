package com.gx.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.blog.entity.Article;
import java.util.List;

public interface ArticleService {
    List<Article> listAll();                           // 查全部（保留）
    Article getById(Long id);                          // 查单个
    void add(Article article);                         // 新增
    void update(Article article);                      // 修改
    void delete(Long id);                              // 删除

    // 🆕 分页 + 条件查询
    Page<Article> search(Integer page, Integer size, String category, String keyword);
}