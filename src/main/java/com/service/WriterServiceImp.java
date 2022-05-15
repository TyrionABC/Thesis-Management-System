package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.WriterMapper;
import com.domain.Paper_Basic_info;
import com.domain.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WriterServiceImp implements WriterService{
    @Autowired
    private WriterMapper writerMapper;
    @Override
    public void insert(Writer writer) {
        writerMapper.insert(writer);
    }

    @Override
    public void delete(Writer writer) {
        Map<String,Object> columnMap=new HashMap<>();
        columnMap.put("id",writer.getId());
        columnMap.put("writerId",writer.getWriterId());
        columnMap.put("writerName",writer.getWriterName());
        writerMapper.deleteByMap(columnMap);
    }

    @Override
    public void update(Writer writer) {
        writerMapper.updateById(writer);
    }

    @Override
    public List<Writer> selectWriters(String id) {
        QueryWrapper<Writer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return writerMapper.selectList(queryWrapper);
    }

    @Override
    public List<Paper_Basic_info> selectPapers(String writerName) {
        return writerMapper.selectPapersByWriter(writerName);
    }
}
