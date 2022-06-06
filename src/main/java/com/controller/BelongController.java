package com.controller;

import com.domain.Belong;
import com.service.BelongService;
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
public class BelongController {
    @Autowired
    private BelongService belongService;
    @GetMapping("/belong")
    public String getAll(Model model){
        List<Belong> belongs=belongService.getAll();
        model.addAttribute("allBelongs",belongs);
        return "admin/belong";
    }
    @PostMapping("/belong/save")
    public String save(@RequestBody Belong belong, RedirectAttributes redirectAttributes){
        if(belong.getId()==null||belong.getDirectionName()==null||"".equals(belong.getId())||"".equals(belong.getDirectionName())){
            redirectAttributes.addFlashAttribute("message","论文名或者方向名为空，请重新输入");
        }
        else if(belongService.selectBelong(belong)!=null){
            redirectAttributes.addFlashAttribute("message","该从属关系已经存在");
        }
        else {
            belongService.insertBelong(belong);
        }
        return "redirect:/admin/belong";
    }
    @DeleteMapping("/belong/delete")
    public String delete(@RequestBody Belong belong,RedirectAttributes redirectAttributes){
        if((belong.getId()==null&&belong.getDirectionName()==null)||("".equals(belong.getDirectionName())&&"".equals(belong.getId()))){
            redirectAttributes.addFlashAttribute("message","未填写论文名和方向名");
        }
        else {
            //只填写id，删除该论文所有记录；只填写directionName，删除该方向所有记录；二者皆有，则只删除该记录
            belongService.deleteBelongs(belong);
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/belong";
    }
    @PostMapping("/belong/update/{directionName}")
    public String update(@RequestBody Belong belong,@PathVariable String directionName,RedirectAttributes redirectAttributes){
        if ("".equals(directionName)){
            redirectAttributes.addFlashAttribute("message","请输入方向名");
        }
        else if(belong.getId()==null||belong.getDirectionName()==null||"".equals(belong.getId())||"".equals(belong.getDirectionName())){
            redirectAttributes.addFlashAttribute("message","请输入完整的要修改的论文信息");
        }
        else if(belongService.selectBelong(belong)!=null){
            redirectAttributes.addFlashAttribute("message","该从属关系已经存在");
        }
        else {
            Belong belong1 = new Belong();
            belong1.setId(belong.getId());
            belong1.setDirectionName(directionName);
            System.out.println(belong1);
            belongService.deleteBelongs(belong1);
            belongService.insertBelong(belong);
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/belong";
    }
    @CrossOrigin
    @PostMapping("/updatePaperBelong")
    @ResponseBody
    public String updatePaper(@RequestBody Map<String,String> directions){
        String paperId=directions.get("Id");
        for (Map.Entry<String, String> entry : directions.entrySet()) {
            String mapKey = entry.getKey();
            if ("Id".equals(mapKey))
                continue;
            String mapValue = entry.getValue();
            //mapKey为奇数表示删除,mapKey为偶数表示添加
            if (Integer.parseInt(mapKey)%2==0){
                System.out.println("添加"+mapValue);
                belongService.insertBelong(new Belong(mapValue,paperId));
            }
            else {
                System.out.println("删除"+mapValue);
                //belongService.deleteBelongs(new Belong(mapValue,paperId));
            }
        }
        return "true";
    }


}
