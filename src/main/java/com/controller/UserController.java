package com.controller;

import com.dao.UserMapper;
import com.domain.Id;
import com.domain.User;
import com.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @CrossOrigin
    @GetMapping("/getAllUsers")
    @ResponseBody
    public List<User> getAll(){
        System.out.println(userMapper.selectList(null));
        return userMapper.selectList(null);
    }
    @CrossOrigin
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @CrossOrigin
    @GetMapping("/register")
    public String register(){
        return "admin/register";
    }

    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    public String Login(@RequestBody User user1,HttpSession session, RedirectAttributes attributes){
        User user=userService.selectUserByUserIdAndPassword(user1.getUserId(),user1.getPassword());
        System.out.println(user1);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            attributes.addFlashAttribute("message", "true");
            return "true";
        } else{
            attributes.addFlashAttribute("message", "false");//随着重定向，将错误信息传递到页面
            return "false";
        }
    }
    @CrossOrigin
    @PostMapping("/register")
    @ResponseBody
    public String Register(@RequestBody User user1,HttpSession session,RedirectAttributes attributes){
        if(userService.selectUserById(user1.getUserId())!=null){
            attributes.addFlashAttribute("message", "该userId已经存在");
            return "false";
            //重定向到控制器的某方法
        }
        else {
            System.out.println(session.getAttribute("user"));
            User user=new User();
            System.out.println(user);
            user.setName(user1.getName());
            user.setUserId(user1.getUserId());
            user.setPassword(user1.getPassword());
            user.setPermission(false);
            userService.insertUser(user);
            return "true";
        }
    }
    @CrossOrigin
    @GetMapping("/logout")
    public String Logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/users";
    }
    @CrossOrigin
    @PostMapping("/updateUser")
    @ResponseBody
    public String updateInfo(@RequestBody User user1){
        userService.updateUser(user1);
        return "true";
    }
    @CrossOrigin
    @PostMapping("/getUserDetails")
    @ResponseBody
    public JSONObject getUserDetails(@RequestBody Id userId){
        JSONObject jsonObject=new JSONObject();
        User user=userService.selectUserById(userId.getUserId());
        jsonObject.put("direction",user.getDirection());
        jsonObject.put("gender",user.getGender());
        jsonObject.put("name",user.getName());
        jsonObject.put("password",user.getPassword());
        jsonObject.put("school",user.getSchool());
        return jsonObject;
    }

}
