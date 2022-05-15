package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Direction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface DirectionMapper extends BaseMapper<Direction> {
    @Select("select * from direction where direction_name=#{name}")
    Direction selectDirectionByName(String name);
    @Delete("delete from direction where direction_name=#{name}")
    void deleteDirectionByName(String name);
}
