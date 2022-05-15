package com.controller;

import com.dao.UserMapper;
import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/getAll")
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
    @PostMapping("/login")
    public String Login(@RequestParam(value = "userId") String userId, @RequestParam(value = "password") String password,
                        HttpSession session, RedirectAttributes attributes){
        User user=userService.selectUserByUserIdAndPassword(userId,password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else{
            attributes.addFlashAttribute("message", "用户名和密码错误");//随着重定向，将错误信息传递到页面
            return "redirect:/users";
        }
    }
    @PostMapping("/register")
    public String Register(@RequestParam(value = "userId") String userId, @RequestParam(value = "password") String password,
                           @RequestParam(value = "name") String name,HttpSession session,RedirectAttributes attributes){
        if(userService.selectUserById(userId)!=null){
            attributes.addFlashAttribute("message", "该userId已经存在");
            return "admin/register";
            //重定向到控制器的某方法
        }
        else {
            System.out.println(session.getAttribute("user"));
            User user=new User();
            System.out.println(userId);
            user.setName(name);
            user.setUserId(userId);
            user.setPassword(password);
            user.setPermission(false);
            userService.insertUser(user);
            return "admin/login";
        }
    }
    @GetMapping("/logout")
    public String Logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/users";
    }

}