package com.gx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.blog.entity.Article;
import com.gx.blog.mapper.ArticleMapper;
import com.gx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> listAll() {
        return articleMapper.selectList(null);
    }

    @Override
    @Cacheable(value = "article", key = "#id")
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void add(Article article) {
        articleMapper.insert(article);
    }

    @Override
    @CacheEvict(value = "article", key = "#article.id")
    public void update(Article article) {
        articleMapper.updateById(article);
    }

    @Override
    @CacheEvict(value = "article", key = "#id")
    public void delete(Long id) {
        articleMapper.deleteById(id);
    }

    // 🆕 分页 + 条件查询
    @Override
    @Cacheable(value = "articles", key = "#page + '-' + #size + '-' + #category + '-' + #keyword")
    public Page<Article> search(Integer page, Integer size, String category, String keyword) {
        // 1. 创建分页对象
        Page<Article> pageObj = new Page<>(page, size);

        // 2. 构建查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        // 按分类筛选（如果传了 category 参数）
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }

        // 关键词搜索标题和内容（如果传了 keyword 参数）
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w
                    .like("title", keyword)
                    .or()
                    .like("content", keyword)
            );
        }

        // 按创建时间倒序排列（最新的文章在最前面）
        wrapper.orderByDesc("create_time");

        // 3. 执行分页查询
        return articleMapper.selectPage(pageObj, wrapper);
    }
}