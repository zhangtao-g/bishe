package com.zt.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Course;
import com.zt.edu.entity.Teacher;
import com.zt.edu.entity.vo.CourseInfoVo;
import com.zt.edu.entity.vo.CoursePublishVo;
import com.zt.edu.entity.vo.CourseQuery;
import com.zt.edu.entity.vo.TeacherQuery;
import com.zt.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 * 课程添加
 * 1.创建vo实体类用于表单数据的封装
 * 2.把表单提交过来的数据添加数据库
 *      向两张表中添加数据 课程表和课程描述表
 * 3.讲师和分类是下拉列表显示
 *      课程分类做成二级分类
 *
 *
 *
 *
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

//返回添加之后的课程id，为了后面添加大纲使用
    @PostMapping("addCourseInfo")
    public UnResult addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
      String id=  courseService.saveCourseInfo( courseInfoVo);

        return UnResult.ok().data("courseId",id);
    }


    //进行数据回显，根据地址栏中的courseId取得数据，
    @GetMapping("getCourseInfo/{courseId}")
    public UnResult getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return UnResult.ok().data("courseInfoVo",courseInfoVo);
    }

    //数据回显之后，进行修改数据信息

    @PostMapping("updateCourseInfo")
    public UnResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);

        return UnResult.ok();
    }

    //根据courseid来查询课程确认信息，
    @GetMapping("getPublishCourseInfo/{id}")
    public UnResult getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.publishCourseInfo(id);
        return UnResult.ok().data("publishCourseInfo",coursePublishVo);

    }

    //设置课程发布
    //修改课程发布状态
    @PostMapping("publishCourse/{id}")
    public UnResult publishCourse(@PathVariable String id){
        Course course=new Course();
        course.setId(id);
        course.setStatus("Normal");//修改课程发布状态
        courseService.updateById(course);
        return UnResult.ok();
    }

    @GetMapping("getCourseList")
    public UnResult getCourseList(){
        List<Course> list = courseService.list(null);
        return UnResult.ok().data("list",list);
    }
    /**
     * 课程列表的实现
     * TODO 实现条件查询带分页
     */
    @GetMapping("pageCourse/{current}/{limit}")
    public UnResult PageListCourse(@PathVariable Long current,
                                    @PathVariable Long limit
    ) {
        //创建page对象
        Page<Course> pageCourse = new Page<>(current, limit);

        //调用方法实现分页
        //调用方法的时候，底层封装，把分页的数据封装到pageTeacher对象里面
        courseService.page(pageCourse, null);
        Long total = pageCourse.getTotal();//总记录数
        List<Course> records = pageCourse.getRecords();//数据list集合

        return UnResult.ok().data("total", total).data("rows", records);
    }




    /**
     * 条件组合查询
     *
     * @RequestBody 使用json传递数据，把json数据封装到对应的对象里面
     * 需要post提交方式,required = false参数值可以为空
     * @ResponeBody 返回数据，返回json
     * TeacherQuery teacherQuery  将条件wapper封装在对象中,进行条件查询
     */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public UnResult pageCourseCondition(@PathVariable Long current,
                                         @PathVariable Long  limit,
                                         @RequestBody(required = false) CourseQuery courseQuery
    ){//把
        //创建page对象
        Page<Course> pageCourse = new Page<>(current, limit);

        //构造条件
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        //多条件组合查询
        //与mybatis种动态sql相似
        //判断条件值是否否为空，不为空拼接条件
        String title = courseQuery.getTitle();
        String  status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        System.out.println(status);
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);//等于status
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//大于等于begin
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);//小于等于end
        }
        //调用方法实现查询分页
        wrapper.orderByDesc("gmt_create");//页面显示的时候实现按时间排序
        courseService.page(pageCourse,wrapper);
        Long total = pageCourse.getTotal();//总记录数
        List<Course> records = pageCourse.getRecords();//数据list集合
        return UnResult.ok().data("total", total).data("rows", records);

    }



    //删除课程
    @DeleteMapping("{id}")
    public UnResult deleteCourse(@PathVariable String id){
        courseService.removeCourse(id);
        return  UnResult.ok();
    }


}

