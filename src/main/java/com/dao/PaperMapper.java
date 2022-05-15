package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Paper_Basic_info;
//import com.domain.Query;
import com.domain.Query;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//⽀持按照研究⽅向、论⽂标题、论⽂类型、论⽂摘要模糊查询、作者、发布⼈、
//会议等条件筛选或查询，以及组合查询。具体实现度⾃⾏决定。
//列表查询结果⽀持分页、排序
@Mapper
public interface PaperMapper extends BaseMapper<Paper_Basic_info> {
//    需要模糊查询:
//    1.使用 $ 占位符:使用 $ 作为占位符，会存在 sql 注入问题，生产环境中应当避免使用
//    2.使用数据库函数 concat:concat 属于数据库函数，MySQL 和 Oracle 都支持，用于字符串连接，而且可以使用 # 作为占位符，防止 SQL 注入,
//      但是有些公司并不推荐使用数据库函数，因为可能会切换数据库
//    3.使用 Mybatis 的动态 sql 标签
    @Select({"<script>" +
            "select * from paper_basic_info left join belong on paper_basic_info.id=belong.id " +
            "left join note_and_extra_file on paper_basic_info.id=note_and_extra_file.id " +
            "left join paper_publish on paper_basic_info.id=paper_publish.id " +
            "left join writer on paper_basic_info.id=writer.id " +
            "left join user on paper_basic_info.publisher_id=user.user_id " +
            "<where>" +
            "<if test='query.directionName!=null'>belong.direction_name like concat('%',#{query.directionName},'%')</if>" +
            "<if test='query.title!=null'>and paper_basic_info.title like concat('%',#{query.title},'%')</if>" +
            "<if test='query.thesisType!=null'>and paper_basic_info.thesis_type like concat('%',#{query.thesisType},'%')</if>" +
            "<if test='query.overview!=null'>and note_and_extra_file.overview like concat('%',#{query.overview},'%')</if>" +
            "<if test='query.publishMeeting!=null'>and paper_publish.publish_meeting=#{query.publishMeeting}</if>" +
            "<if test='query.name!=null'>and writer.writer_name=#{query.name}</if>" +
            "<if test='query.userName!=null'>and user.name=#{query.userName}</if>" +
            "</where>" +
            "</script>"})
    List<Paper_Basic_info> getPapersByConditions(@Param("query") Query query);

    @Select("select * from paper_basic_info where id=#{id}")
    Paper_Basic_info selectPaperById(String id);

    @Delete("delete from paper_basic_info where id=#{id}")
    void deletePaperById(String id);

    @Select("select * from paper_basic_info where title=#{title}")
    Paper_Basic_info selectPaperByTitle(String title);


}