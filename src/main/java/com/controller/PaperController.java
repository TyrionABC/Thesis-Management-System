package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.domain.Comment;
import com.domain.Paper_Basic_info;
import com.domain.Paper_publish;
import com.domain.Query;
import com.service.*;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class PaperController {
    //根据用户id查找所有论文，笔记，
    // 点赞数（论文），
    //修改用户信息
    //近期文章（20）
    //用户单位，研究方向,邮箱是id
    //⽀持按照研究⽅向、论⽂标题、论⽂类型、论⽂摘要模糊查询、作者、发布⼈、
    //会议等条件筛选或查询，以及组合查询。具体实现度⾃⾏决定。
    //列表查询结果⽀持分页、排序
    @Autowired
    private PaperService paperService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DirectionService directionService;
    @Autowired
    private NoteAndFileService noteAndFileService;
    @Autowired
    private PublishService publishService;
    @Autowired
    private UserService userService;
    @Autowired
    private WriterService writerService;
    //呈现论文,传入根据什么进行排序，以及页面的大小和页数，即分页查询##
    @GetMapping("/paper")
    public String paper(Model model){
        QueryWrapper<Paper_Basic_info> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("title");
        Map<String,Object> paperPage =paperService.selectPage(1,5,queryWrapper);
        List<Paper_Basic_info> allPapers=paperService.selectAll();//所有数据
        System.out.println(paperPage);
        model.addAttribute("paperPage",paperPage);//第一页，前五条
        model.addAttribute("allPapers",allPapers);//返回所有数据
        return "admin/paper";
    }
    //查找论文，⽀持按照研究⽅向、论⽂标题、论⽂类型、论⽂摘要模糊查询、作者、发布⼈、会议等条件筛选或查询，以及组合查询
    //列表查询结果⽀持分页、排序
    @PostMapping("/select")
    public String select(@RequestBody Query query,Model model){
        List<Paper_Basic_info> papers=paperService.selectPapersByConditions(query);
        model.addAttribute("papers",papers);
        return "admin/paper";
    }
    //查询某篇论文详细信息，包括笔记、评论和其他信息。
    public String getDetails(Model model){
        List<Comment> comments=null;
        return null;
    }
    //删除论文
    @DeleteMapping("/delete/{id}")
    public String paper(@PathVariable String id,RedirectAttributes attributes){
        boolean flag=paperService.deletePaperById(id);
        if(flag){
            attributes.addFlashAttribute("message", "该论文名称不存在");
        }
        else{
            attributes.addFlashAttribute("message", "操作成功");
        }
            return "redirect:/admin/paper";
    }
    //修改论文信息##
    @PostMapping("/update/{id}")
    public String update(@RequestBody Paper_Basic_info paper,@PathVariable String id,RedirectAttributes attributes){
        if(paperService.selectPaperById(id)==null){
            attributes.addFlashAttribute("message", "要修改的论文不存在");
        }
        else {
            paperService.updatePaper(paper,id);
        }
        return "redirect:/admin/paper";

    }

    //插入论文##
    @PostMapping("/input")
    public String input(@RequestBody Paper_Basic_info paper, RedirectAttributes attributes){
        boolean flag=paperService.insertPaper(paper);
        if(!flag){
            attributes.addFlashAttribute("message", "添加失败，该论文已经存在");
        }
        else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/paper";
    }




}
