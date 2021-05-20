package com.zt.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Course;
import com.zt.edu.entity.Teacher;
import com.zt.edu.service.CourseService;
import com.zt.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/5/12 20:51
 * @note:
 */

@RestController
@RequestMapping("/eduservice/teacherfront")

public class TeacherFrontController {


    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;



    //分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public UnResult getTeacherFrontList(@PathVariable Long page,@PathVariable Long limit){
        Page<Teacher> pageTeacher=new Page<>(page,limit);
        Map<String,Object> map=teacherService.getTeahcerFrontList(pageTeacher);

        //返回分页所有数据
        return UnResult.ok().data(map);
    }


    //2.讲师详情页的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public UnResult getTeacherFrontInfo(@PathVariable String teacherId){
        System.out.println("teacherId"+teacherId);
        //1.根据讲师id查询讲师的基本信息
        Teacher teacher=teacherService.getById(teacherId);
        //2.根据讲师id查询课程
        System.out.println("teacher"+teacher);


        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList=courseService.list(wrapper);
        System.out.println(courseList);
        return UnResult.ok().data("teacher",teacher).data("courseList",courseList);
    }

}
