package com.zt.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Course;
import com.zt.edu.entity.Teacher;
import com.zt.edu.service.CourseService;
import com.zt.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/9 10:30
 * @note:  前台前端页面列出八条热门课程和4位热门老师
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin//解决跨域问题
public class IndexFrontController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    //列出八条热门课程和4位热门老师

    @GetMapping("index")
    public UnResult index(){

        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<Teacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapperTeacher);

        return UnResult.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
