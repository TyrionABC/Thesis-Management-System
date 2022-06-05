package com.controller;

import com.dao.CommentMapper;
import com.dao.UserMapper;
import com.domain.Comment;
import com.service.CommentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserMapper userMapper;
    //获取该文章所有评论
    @CrossOrigin
    @ResponseBody
    @PostMapping("/selectAllComments")
    public JSONArray getAll(@RequestBody Comment comment){
        JSONArray jsonArray=new JSONArray();
        List<Comment> comments=commentService.selectAll(comment.getId());
        for(Comment comment1:comments){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("commentId",comment1.getCommentId());
            jsonObject.put("content",comment1.getContent());
            jsonObject.put("date",comment1.getId());
            jsonObject.put("parentCommentId",comment1.getParentCommentId());
            jsonObject.put("userName",userMapper.selectUserById(comment1.getUserId()).getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //删除评论
    @CrossOrigin
    @ResponseBody
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestBody Comment comment){
        commentService.delete(comment.getCommentId());
        return "true";
    }
    //更改评论
    @CrossOrigin
    @ResponseBody
    @PostMapping("/updateComment")
    public String updateComment(@RequestBody Comment comment){
        commentService.update(comment);
        return "true";
    }
    //添加评论
    @CrossOrigin
    @ResponseBody
    @PostMapping("/insertComment")
    public String insert(@RequestBody Comment comment){
        commentService.insert(comment);
        return "true";
    }

}
