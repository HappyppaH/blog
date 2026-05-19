package com.gx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gx.blog.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}