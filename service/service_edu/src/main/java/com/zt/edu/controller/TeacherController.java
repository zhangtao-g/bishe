package com.zt.edu.controller;


import com.zt.edu.entity.Teacher;
import com.zt.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    //把service注入
    private TeacherService teacherService;
    //查询讲师列表中所有数据

    @GetMapping("findAll")
    public List<Teacher> findAll(){
        List<Teacher> lists=teacherService.list(null);
        return lists;
    }

}

