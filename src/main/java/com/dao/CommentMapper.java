package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select * from comment where parent_comment_id is null and id=#{id}")
    List<Comment> selectRoots(String id);
    @Select("select * from comment where parent_comment_id=#{commentId}")
    List<Comment> selectReplies(String commentId);
}
