package com.gx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gx.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}