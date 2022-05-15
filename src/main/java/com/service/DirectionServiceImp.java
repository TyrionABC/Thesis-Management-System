package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.DirectionMapper;
import com.domain.Direction;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectionServiceImp implements DirectionService{
    @Autowired
    private DirectionMapper directionMapper;
    @Override
    public void insertDirection(Direction direction) {
        if(direction==null) {
            System.out.println("尚未为该方向添加内容");
            //direction为空应如何判断？
        }
        else {
            if(directionMapper.selectDirectionByName(direction.getDirectionName())!=null){
                System.out.println("该方向已经存在，无需添加");
            }
            else{
                directionMapper.insert(direction);
            }
        }
    }

    @Override
    public void deleteDirectionByName(String name) {
        directionMapper.deleteDirectionByName(name);
    }

    @Override
    public void updateDirection(String name,Direction direction) throws NotFoundException {
        Direction direction1=directionMapper.selectDirectionByName(name);
        if(direction1==null){
            throw new NotFoundException("there is no such direction");
        }
        else {
            direction1.setDirectionName(name);
            directionMapper.updateById(direction);
        }
    }

    @Override
    public Direction selectDirectionByName(String name) {
        return directionMapper.selectDirectionByName(name);
    }
    public List<Direction> selectAll(){
        return directionMapper.selectList(null);
    }

    @Override
    public Map<String,Object> selectPage(int page,int size,QueryWrapper<Direction> queryWrapper) {
        Page<Direction> pageInfo=new Page<>(page,size);
        IPage<Direction> directionIPage=directionMapper.selectPage(pageInfo,queryWrapper);
        Map<String,Object> pageMap=new HashMap<>(3);
        pageMap.put("total_record",directionIPage.getTotal());
        pageMap.put("total_pages",directionIPage.getPages());
        pageMap.put("current_data",directionIPage.getRecords());
        return pageMap;
    }

}
