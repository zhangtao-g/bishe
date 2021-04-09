package com.zt.edu.controller;


import com.zt.edu.entity.Teacher;
import com.zt.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
//设置请求父路径/edu/teacher
@RequestMapping("/edu/teacher")
//@GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class TeacherController {

    @Autowired
    //把service注入
    private TeacherService teacherService;
    //查询讲师列表中所有数据

    @GetMapping("findAll")
    //@GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)
    public List<Teacher> findAll(){
        List<Teacher> lists=teacherService.list(null);
        return lists;
    }

//自己写的超简单的逻辑删除，
    @GetMapping("delete")
    public void deleted(){

        teacherService.removeById("1");
    }
    //逻辑删除讲师方法
    // @DeleteMapping("{id}")不能直接用浏览器进行测试，
    //创建公共的模块，整合swagger，为了所有模块都能进行使用
    @DeleteMapping("{id}")//通过路径传值
    public boolean remoteTeacher(@PathVariable String id){
        Boolean flag =teacherService.removeById(id);
        return flag;

    }
    //逻辑删除讲师方法

//    @GetMapping("{id}")//通过路径传值
//    public boolean remoteTeacher(@PathVariable String id){
//        Boolean flag =teacherService.removeById(id);
//        return flag;
//
//    }
}

