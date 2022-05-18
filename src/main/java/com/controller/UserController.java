package com.controller;

import com.dao.UserMapper;
import com.domain.User;
import com.service.UserService;
import org.apache.ibatis.jdbc.Null;
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
    @GetMapping("/getAllUsers")
    @ResponseBody
    public List<User> getAll(){
        System.out.println(userMapper.selectList(null));
        return userMapper.selectList(null);
    }
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @GetMapping("/register")
    public String register(){
        return "admin/register";
    }

//    public String Login(@RequestParam(value = "userId") String userId, @RequestParam(value = "password") String password,
//                        HttpSession session, RedirectAttributes attributes)
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
//    @PostMapping("/register")
//    public String Register(@RequestParam(value = "userId") String userId, @RequestParam(value = "password") String password,
//                           @RequestParam(value = "name") String name,HttpSession session,RedirectAttributes attributes){
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
    @GetMapping("/logout")
    public String Logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/users";
    }

}
