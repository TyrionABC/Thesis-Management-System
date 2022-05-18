package com.example.labbb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.*;
import com.domain.*;
import com.service.*;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LabbbApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DirectionMapper directionMapper;
    @Autowired
    private DirectionService directionService;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperService paperService;
    @Autowired
    private WriterService writerService;
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private PublishService publishService;
    @Autowired
    private NoteAndFileService noteAndFileService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Test
    void contextLoads() {
        List<User> users=userMapper.selectList(null);
        System.out.println(users);
    }
    @Test
    void getUserById(){
        User user=userMapper.selectUserById("13663067562");
        System.out.println(user);
    }
    @Test
    void getUser(){
        User user=userService.selectUserByUserIdAndPassword("1216776075","m1216776075");
        System.out.println(user);
    }
    @Test
    void insertUser(){
        User user=new User();
        user.setUserId("123");
        user.setPermission(true);
        user.setPassword("123456");
        user.setName("1111111");
        userService.insertUser(user);
    }
    @Test
    void deleteUser(){
        userService.deleteUserByUserId("mb");
    }
    @Test
    void selectDirection(){
        Direction direction=directionService.selectDirectionByName("language");
        System.out.println(direction);
    }
    @Test
    void insertDirection(){
        Direction direction=new Direction();
        direction.setDirectionName("chinese");
        direction.setParentDirectionName("language");
        direction.setLevel(2);
        direction.setPath("language-chinese");
        directionService.insertDirection(direction);
    }
    @Test
    void updateDirection() throws NotFoundException {
        Direction direction=new Direction();
        String name="spring";
        direction.setDirectionName("spring");
        direction.setParentDirectionName("cs");
        direction.setLevel(2);
        direction.setPath("cs-spring");
        directionService.updateDirection(name,direction);
    }
    @Test
    void deleteDirection(){
        directionService.deleteDirectionByName("chinese");
    }
    @Test
    void selectAllDirections(){
        System.out.println(directionService.selectAll());
    }
    @Test
    void getPage(){
        //先找才有record
        QueryWrapper<Direction> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("direction_name");
        Map<String,Object> pageMap=directionService.selectPage(1,4,queryWrapper);
        System.out.println(pageMap);
    }
    @Test
    void getPaper(){
        System.out.println(paperMapper.selectPaperById("0"));
    }
    @Test
    void getPaperByTitle(){
        System.out.println(paperMapper.selectPaperByTitle("MyBatis简介"));
    }
    @Test
    void insertPaper(){
        Paper_Basic_info paper=new Paper_Basic_info();
        paper.setLiteratureLink("https://www.cnblogs.com/minmin123/p/14590360.html");
        paper.setPublisherId("1216776075");
        paper.setTitle("前后端之间的数据传递");
        paper.setThesisType("tool");
        paperService.insertPaper(paper);
    }
    @Test
    void insertWriter(){
        Writer writer = new Writer();
        writer.setWriterName("tyrion");
        writer.setId("22");
        writer.setLevel(1);
        writerService.insert(writer);
    }
    @Test
    void selectPaperByWriter(){
        List<Paper_Basic_info> papers = writerService.selectPapers("tyrion");
        System.out.println(papers);
    }
    @Test
    void selectWriters(){
        System.out.println(writerService.selectWriters("22"));
    }
    @Test
    void selectPapersByPublishInfo(){
        Paper_publish publish=new Paper_publish();
        publish.setPublisher("tyrion");
        publish.setPublisherId("1216776075");
        publish.setPublishMeeting("三和会议");
        System.out.println(publishService.selectByPublishInfo(publish));
        //System.out.println(publishMapper.selectPaperByPublishInfo(publish.getPublisher(),publish.getPublisherId(),publish.getPublishMeeting(),publish.getPublishTime()));
    }
    @Test
    void insertPublish(){
        Paper_publish publish=new Paper_publish();
        publish.setId("6c85aac5-2");
        publish.setPublisher("tyrion");
        publish.setPublisherId("1216776075");
        publish.setPublishMeeting("上海会议");
        publishService.insert(publish);
    }
    @Test
    void insertNote(){
        Note_and_extra_file note_and_extra_file=new Note_and_extra_file();
        //note_and_extra_file.setExtraFile();
        //note_and_extra_file.setNote();
        note_and_extra_file.setId("2");
        note_and_extra_file.setPublisherId("1216776075");
        note_and_extra_file.setOverview("计算机的发展过程");
        noteAndFileService.insert(note_and_extra_file);
    }
    @Test
    void deleteNote(){
        noteAndFileService.delete("0");
    }
    @Test
    void update(){
        Note_and_extra_file note_and_extra_file=new Note_and_extra_file();
        //note_and_extra_file.setExtraFile();
        //note_and_extra_file.setNote();
        note_and_extra_file.setId("2");
        note_and_extra_file.setPublisherId("1216776075");
        note_and_extra_file.setOverview("计算机的种类");
        noteAndFileService.update(note_and_extra_file);
    }
    @Test
    void selectNote(){
        System.out.println(noteAndFileService.select("2"));
    }
    @Test
    void insertComment(){
        Comment comment = new Comment();
        comment.setId("0");
        comment.setContent("长风破浪会有时，直挂云帆济沧海");
        comment.setUserId("1216776075");
        commentService.insert(comment,"d25bc80e-4");
    }
    @Test
    void deleteComment(){
        commentService.delete("0932b08f-3");
    }
    @Test
    void selectAll(){
        System.out.println(commentService.selectAll("0"));
    }
    @Test
    void selectPapersByConditions(){
        Query query = new Query();
        //query.setUserName("tyrion");
        //query.setName("tyrion");
        query.setPublishMeeting("上海会议");
        //query.setOverview("app");
        //query.setTitle("到的Page类");
        //query.setThesisType("s");
        //query.setDirectionName("sp");
        System.out.println(paperMapper.getPapersByConditions(query));
    }
    @Test
    void updatePublish(){
        Paper_publish publish=new Paper_publish();
        publish.setId("6c85aac5-2");
        publish.setPublisher("tyrion");
        publish.setPublisherId("1216776075");
        publish.setPublishMeeting("上海会议");
        publish.setPublishTime(new Date());
        publishService.update(publish);
    }
    @Test
    void getRootComments(){
        System.out.println(commentMapper.selectRoots("0"));
    }
    @Test
    void getReplies(){
        System.out.println(commentMapper.selectReplies("2357e838-c"));
    }
    @Test
    void getAllComments(){
        System.out.println(commentService.selectAll("0"));
    }

}
