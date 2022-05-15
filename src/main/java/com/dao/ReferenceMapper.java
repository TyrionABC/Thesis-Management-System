package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Reference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ReferenceMapper extends BaseMapper<Reference> {
    @Select("select * from reference where id=#{id}")
    List<Reference> getAllReferences(String id);
    @Delete("delete from reference where id=#{id}")
    void deleteReferenceById(String id);
    @Delete("delete from reference where refer_paper_id=#{referId}")
    void deleteReferenceByReferId(String referId);
    @Delete("delete from reference where id=#{id} and refer_paper_id=#{referId}")
    void deleteReference(String id,String referId);
    @Select("select * from reference where id=#{id} and refer_paper_id=#{referId}")
    Reference selectReference(String id,String referId);

}
