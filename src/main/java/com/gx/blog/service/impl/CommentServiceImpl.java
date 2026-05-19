package com.gx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.blog.entity.Comment;
import com.gx.blog.mapper.CommentMapper;
import com.gx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listByArticleId(Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId)
                .orderByDesc("create_time");  // 最新评论在最上面
        return commentMapper.selectList(wrapper);
    }

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }
}