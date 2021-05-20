package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.entity.subject.OneSubject;
import com.zt.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/eduservice/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //添加课程分类
    //获取上传的文件，把文件内容读出来
    @PostMapping("addSubject")
    public UnResult addSubject(MultipartFile file){
        //上传excel文件
        subjectService.saveSubject(file,subjectService);
        return UnResult.ok();
    }
    //课程分类列表
    @GetMapping("getAllSubject")
    public UnResult getAllSubject(){
        //在一级分类中已经包含了二级分类，只返回一级分类就能取到所有的值
      List<OneSubject>list= subjectService.getAllOneTwoSubject();
        return UnResult.ok().data("list",list);
    }




}

