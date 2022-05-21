package com.controller;

import com.domain.Note_and_extra_file;
import com.domain.Paper;
import com.domain.Paper_Basic_info;
import com.domain.Query;
import com.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// data: ['前端', '后端', 'Android', 'IOS', '软件测试', '人工智能', '机器学习', '深度学习', '数据库', '网络安全']

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
    @CrossOrigin
    @PostMapping("/myNotes")
    @ResponseBody
    public JSONArray myNotes(@RequestParam String userId){
        System.out.println(userId);
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
        return json;
    }

    //获取用户最近发布的20篇论文
    @CrossOrigin
    @GetMapping("/paper")
    @ResponseBody
    public JSONArray paper(){
        List<Paper> allPapers=paperService.selectNewPapers();//所有数据
        JSONArray json = new JSONArray();
        // 需要添加作者、发布人姓名、研究方向、发布会议
        for(Paper paper : allPapers){
            JSONObject jo = new JSONObject();
            jo.put("id", paper.getId());
            jo.put("literatureLink", paper.getLiteratureLink());
            jo.put("publisherId",paper.getPublisherId());
            jo.put("thesisDate",paper.getThesisDate());
            jo.put("thesisType",paper.getThesisType());
            jo.put("title",paper.getTitle());
            jo.put("like",paper.getLike());
            json.add(jo);
        }
        return json;
    }
    //获取用户发布的论文
    @CrossOrigin
    @PostMapping("/myPaper")
    @ResponseBody
    public JSONArray myPaper(@RequestParam("userId") String userId){
        List<Paper> allPapers=paperService.selectMyPapers(userId);//所有数据
        System.out.println("flag");
        JSONArray json = new JSONArray();
        for(Paper paper : allPapers){
            JSONObject jo = new JSONObject();
            jo.put("id", paper.getId());
            jo.put("literatureLink", paper.getLiteratureLink());
            jo.put("publisherId",paper.getPublisherId());
            jo.put("thesisDate",paper.getThesisDate());
            jo.put("thesisType",paper.getThesisType());
            jo.put("title",paper.getTitle());
            jo.put("like",paper.getLike());
            json.add(jo);
        }
        return json;
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
            jo.put("writerName",paper.getWriterName());
            jo.put("like",paper.getLike());
            json.add(jo);
        }
        return json;
    }
    //删除论文
    @CrossOrigin
    @PostMapping("/delete")
    @ResponseBody
    public String paper(@RequestParam String id){
        boolean flag=paperService.deletePaperById(id);
        if(flag){
            return "false";
        }
        else{
            return "true";
        }
    }
    //修改论文信息##
    @CrossOrigin
    @PostMapping("/update")
    @ResponseBody
    public String update(@RequestBody Paper_Basic_info paper,@RequestParam String id){
        if(paperService.selectPaperById(id)==null){
            return "false";
        }
        else {
            paperService.updatePaper(paper,id);
            return "true";
        }
    }

    //插入论文##
    @CrossOrigin
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
