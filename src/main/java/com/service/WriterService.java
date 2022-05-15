package com.service;

import com.domain.Paper_Basic_info;
import com.domain.Writer;

import java.util.List;

public interface WriterService {
    void insert(Writer writer);
    void delete(Writer writer);
    //修改作者要传入writerId，根据writerId更改
    void update(Writer writer);
    List<Writer> selectWriters(String id);
    List<Paper_Basic_info> selectPapers(String writerName);
}
