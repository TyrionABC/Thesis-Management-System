package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.domain.*;
import com.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    //获取用户笔记
    @GetMapping("myNotes/{userId}")
    @ResponseBody
    public String myNotes(@PathVariable("userId") String userId){
        List<Note_and_extra_file> allNotes=noteAndFileService.selectMyNotes(userId);//所有数据
        JSONArray json = new JSONArray();
        for(Note_and_extra_file note : allNotes){
            JSONObject jo = new JSONObject();
            jo.put("note", note.getNote());
            jo.put("overview", note.getOverview());
            jo.put("extraFile",note.getExtraFile());
            jo.put("id",note.getId());
            json.add(jo);
        }
        System.out.println(json);
        return json.toString();
    }

    //呈现论文,传入根据什么进行排序，以及页面的大小和页数，即分页查询##
    @GetMapping("/paper")
    @ResponseBody
    public String paper(){
        QueryWrapper<Paper_Basic_info> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("title");
        Map<String,Object> paperPage =paperService.selectPage(1,5,queryWrapper);
        List<Paper_Basic_info> allPapers=paperService.selectAll();//所有数据
        JSONArray json = new JSONArray();
        for(Paper_Basic_info paper : allPapers){
            JSONObject jo = new JSONObject();
            jo.put("id", paper.getId());
            jo.put("literatureLink", paper.getLiteratureLink());
            jo.put("publisherId",paper.getPublisherId());
            jo.put("thesisDate",paper.getThesisDate());
            jo.put("thesisType",paper.getThesisType());
            jo.put("title",paper.getTitle());
            json.add(jo);
        }
        return ""+json.size();
    }
    //获取用户发布的所有论文
    @GetMapping("/myPaper/{userId}")
    @ResponseBody
    public String myPaper(@PathVariable("userId") String userId){
        List<Paper> allPapers=paperService.selectMyPapers(userId);//所有数据
        JSONArray json = new JSONArray();
        for(Paper paper : allPapers){
            JSONObject jo = new JSONObject();
            jo.put("id", paper.getId());
            jo.put("literatureLink", paper.getLiteratureLink());
            jo.put("publisherId",paper.getPublisherId());
            jo.put("thesisDate",paper.getThesisDate());
            jo.put("thesisType",paper.getThesisType());
            jo.put("title",paper.getTitle());
            json.add(jo);
        }
        return json.toString();
    }
    //查找论文，⽀持按照研究⽅向、论⽂标题、论⽂类型、论⽂摘要模糊查询、作者、发布⼈、会议等条件筛选或查询，以及组合查询
    //列表查询结果⽀持分页、排序
    @CrossOrigin
    @PostMapping("/select")
    @ResponseBody
    public JSONArray select(@RequestBody Query query){
        List<Paper> papers=paperService.selectPapersByConditions(query);
        JSONArray json = new JSONArray();
        for(Paper paper : papers){
            JSONObject jo = new JSONObject();
            jo.put("id", paper.getId());
            jo.put("literatureLink", paper.getLiteratureLink());
            jo.put("overview",paper.getOverview());
            jo.put("path",paper.getPath());
            jo.put("publisher",paper.getPublisher());
            jo.put("publisherId",paper.getPublisherId());
            jo.put("publishMeeting",paper.getPublishMeeting());
            jo.put("thesisDate",paper.getThesisDate());
            jo.put("thesisType",paper.getThesisType());
            jo.put("title",paper.getTitle());
            jo.put("writereName",paper.getWriterName());
            json.add(jo);
        }
        return json;
    }
    //查询某篇论文详细信息，包括笔记、评论和其他信息。
    public String getDetails(Model model){
        List<Comment> comments=null;
        return null;
    }
    //删除论文
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String paper(@PathVariable String id,RedirectAttributes attributes){
        boolean flag=paperService.deletePaperById(id);
        if(flag){
            return "false";
        }
        else{
            return "true";
        }
    }
    //修改论文信息##
    @PostMapping("/update/{id}")
    @ResponseBody
    public String update(@RequestBody Paper_Basic_info paper,@PathVariable String id,RedirectAttributes attributes){
        if(paperService.selectPaperById(id)==null){
            return "false";
        }
        else {
            paperService.updatePaper(paper,id);
            return "true";
        }
    }

    //插入论文##
    @PostMapping("/input")
    @ResponseBody
    public String input(@RequestBody Paper_Basic_info paper){
        boolean flag=paperService.insertPaper(paper);
        if(!flag){
            return "false";
        }
        else {
            return "true";
        }
    }




}
