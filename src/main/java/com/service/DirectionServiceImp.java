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
    public boolean insertDirection(Direction direction) {
        //该方向已经存在
        if(directionMapper.selectDirectionByName(direction.getDirectionName())!=null){
            return false;
        }
        else{
            directionMapper.insert(direction);
            return true;
        }
    }

    @Override
    public void deleteDirectionByName(String name) {
        if (directionMapper.selectDirectionByName(name).getLevel()==1){
            List<Direction> directions=directionMapper.selectDirectionByParent(name);
            for(Direction direction:directions){
                direction.setParentDirectionName(direction.getDirectionName());
                direction.setLevel(1);
                direction.setPath(direction.getDirectionName());
                directionMapper.updateById(direction);
            }
        }
        directionMapper.deleteDirectionByName(name);
    }

    @Override
    public void updateDirection(String name,Direction direction){
        if (direction.getParentDirectionName()==null){
            direction.setPath(direction.getDirectionName());
            direction.setParentDirectionName(direction.getDirectionName());
            direction.setLevel(1);
        }
        else{
            direction.setLevel(2);
            direction.setPath(direction.getParentDirectionName()+"-"+direction.getDirectionName());
        }
        directionMapper.updateByName(name,direction);
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

    @Override
    public List<Direction> getDirectionsByParent(String parentDirectionName) {
        return directionMapper.selectDirectionByParent(parentDirectionName);
    }

    @Override
    public List<Direction> getPaperDirection(String id) {
        List<Direction> directions=directionMapper.selectPaperDirections(id);
        return directions;
    }

    @Override
    public List<Direction> getAllParents() {
        return directionMapper.selectAllParents();
    }


}
