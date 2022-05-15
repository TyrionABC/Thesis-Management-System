package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domain.Direction;
import com.service.DirectionService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DirectionController {
    @Autowired
    private DirectionService directionService;
    //分页查询
    @GetMapping("/direction")
    public String types(Model model) {
        QueryWrapper<Direction> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("direction_name");
        Map<String,Object> pageMap=directionService.selectPage(1,4,queryWrapper);
        model.addAttribute("pageMap",pageMap);
        return "admin/direction";
    }
    //尚未完成、、、、、、
    @GetMapping("/direction/input")
    public String input(Model model){
        model.addAttribute("direction", new Direction());
        return "admin/direction-input";
    }
    //查找某方向
    @GetMapping("/direction/select/{directionName}")
    public String editInput(@PathVariable String directionName, Model model) {
        Direction direction = directionService.selectDirectionByName(directionName);
        System.out.println(direction);
        model.addAttribute("direction", direction);
        return "/admin/direction-input";
    }
    //删除某方向
    @DeleteMapping("/direction/delete/{directionName}")
    public String deleteDirection(@PathVariable(value = "directionName") String directionName, RedirectAttributes attributes) {
        directionService.deleteDirectionByName(directionName);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/direction";
    }
    //新增方向
    @PostMapping("/direction")
    public String post(@RequestBody Direction direction, BindingResult result, RedirectAttributes attributes) {
        if(direction.getDirectionName()==null||"".equals(direction.getDirectionName())){
            attributes.addFlashAttribute("message", "请输入方向的名称");
            return "admin/direction-input";
        }
        Direction direction1 = directionService.selectDirectionByName(direction.getDirectionName());
        if (direction1 != null){
            attributes.addFlashAttribute("message", "不可添加重复的方向");
            return "admin/direction-input";
        }
        directionService.insertDirection(direction);
        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/direction";
    }
    //更新方向
    @PostMapping("/direction/{directionName}")
    public String editPost(@RequestBody Direction direction,@PathVariable String directionName, RedirectAttributes attributes) throws NotFoundException {
        if(directionName==null){
            attributes.addFlashAttribute("message", "被更新方向的名称不可为空");
            return "admin/direction-input";
        }
        Direction direction1=directionService.selectDirectionByName(directionName);
        if (direction1==null){
            attributes.addFlashAttribute("message", "要修改的方向不存在");
            return "admin/direction-input";
        }
        directionService.updateDirection(directionName,direction);
        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/direction";
    }

}
