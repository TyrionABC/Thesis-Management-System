package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Paper_Basic_info;
import com.domain.Writer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WriterMapper extends BaseMapper<Writer> {
    @Select("select * from writer left join paper_basic_info on writer.id = paper_basic_info.id where writer_name=#{writerName}")
    List<Paper_Basic_info> selectPapersByWriter(String writerName);
    @Select("select * from writer where id=#{id}")
    List<Writer> selectWritersById(String id);

}
