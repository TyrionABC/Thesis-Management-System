package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Note_and_extra_file;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteAndFileMapper extends BaseMapper<Note_and_extra_file> {
    @Select("select * from note_and_extra_file where publisher_id=#{userId}")
    List<Note_and_extra_file> getMyNotes(String userId);
}
