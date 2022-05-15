package com.controller;

import com.domain.Reference;
import com.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReferenceController {
    @Autowired
    private ReferenceService referenceService;
    //获取所有引用关系
    @GetMapping("/references")
    public String getAll(Model model){
        List<Reference> references=referenceService.getAll();
        model.addAttribute("allReferences",references);
        System.out.println(references);//test
        return "admin/paper";
    }
    @PostMapping("/references/save")
    public String save(@RequestBody Reference reference, RedirectAttributes redirectAttributes){
        if(reference.getId()==null||reference.getReferPaperId()==null){
            redirectAttributes.addFlashAttribute("message","论文id或引用论文的id为空，操作失败");
            //System.out.println("论文id或引用论文的id为空，操作失败");
        }
        else if (referenceService.selectReference(reference)!=null){
            redirectAttributes.addFlashAttribute("message","该引用关系已经存在，操作失败");
            //System.out.println("该引用关系已经存在，操作失败");
        }
        else{
            referenceService.insertReference(reference);
        }
        return "redirect:/admin/references";
    }
    @DeleteMapping("/references/delete")
    public String delete(@RequestBody Reference reference,RedirectAttributes redirectAttributes){
        if(reference.getId()==null&&reference.getReferPaperId()==null){
            redirectAttributes.addFlashAttribute("message","论文id和引用论文的id为空，操作失败");
        }
        else{
            referenceService.deleteReferences(reference);
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/references";
    }
}
