package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.PaperMapper;
import com.domain.Paper;
import com.domain.Direction;
import com.domain.Paper_Basic_info;
import com.domain.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaperServiceImp implements PaperService{
    @Autowired
    private PaperMapper paperMapper;
    @Override
    public boolean insertPaper(Paper_Basic_info paper) {
        if (paperMapper.selectPaperByTitle(paper.getTitle())!=null)
            return false;//出现重名论文
        else{
            String id = UUID.randomUUID().toString().substring(0,10);
            while (paperMapper.selectPaperById(id)!=null){
                id=UUID.randomUUID().toString().substring(0,10);
            }
            paper.setId(id);
            paperMapper.insert(paper);
            return true;
        }
    }

    @Override
    public boolean deletePaperById(String id) {
        if(paperMapper.selectPaperById(id)==null)
            return false;//没有要删除的论文
        else
        paperMapper.deletePaperById(id);
        return true;
    }

    @Override
    public boolean updatePaper(Paper_Basic_info paper, String id) {
        if (paperMapper.selectPaperById(id)==null){
            return false;
        }
        else{
            paper.setId(id);
            paperMapper.updateById(paper);
            return true;
        }
    }

    @Override
    public Paper_Basic_info selectPaperById(String id) {
        return paperMapper.selectPaperById(id);
    }

    @Override
    public Paper_Basic_info selectPaperByTitle(String title) {
        return paperMapper.selectPaperByTitle(title);
    }

    @Override
    public List<Paper_Basic_info> selectAll() {
        return paperMapper.selectList(null);
    }

    @Override
    public Map<String, Object> selectPage(int page, int size, QueryWrapper<Paper_Basic_info> queryWrapper) {
        Page<Paper_Basic_info> pageInfo=new Page<>(page,size);
        IPage<Paper_Basic_info> paperIPage=paperMapper.selectPage(pageInfo,queryWrapper);
        Map<String,Object> pageMap=new HashMap<>(3);
        pageMap.put("total_record",paperIPage.getTotal());
        pageMap.put("total_pages",paperIPage.getPages());
        pageMap.put("current_data",paperIPage.getRecords());
        return pageMap;
    }

    @Override
    public List<Paper> selectPapersByConditions(Query query) {
        return paperMapper.getPapersByConditions(query);
    }

    @Override
    public List<Paper> selectMyPapers(String userId) {
        return paperMapper.getMyPapers(userId);
    }

    @Override
    public List<Paper> selectNewPapers() {
        List<Paper> papers=paperMapper.getNewPapers();
        return papers;
    }

}
