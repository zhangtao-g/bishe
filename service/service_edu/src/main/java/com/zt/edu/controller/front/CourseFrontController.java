package com.zt.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.commonutils.JwtUtils;
import com.zt.commonutils.UnResult;
import com.zt.commonutils.ordervo.CourseWebVoOrder;
import com.zt.edu.client.OrdersClient;
import com.zt.edu.entity.Chapter;
import com.zt.edu.entity.Course;
import com.zt.edu.entity.chapter.ChapterVo;
import com.zt.edu.entity.vo.frontVo.CourseFrontVo;
import com.zt.edu.entity.vo.frontVo.CourseWebVo;
import com.zt.edu.entity.vo.frontVo.OrderVo;
import com.zt.edu.service.ChapterService;
import com.zt.edu.service.CourseService;
import com.zt.edu.service.OrderVoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/5/13 14:51
 * @note:
 */
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {


    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CourseService courseService;


    @Autowired
    private OrderVoService orderVoService;
    @PostMapping("getCourseFrontList/{page}/{limit}")
    public UnResult getFrontCourseList(@PathVariable Long page, @PathVariable Long limit,
                                       @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseFrontVo);
        //返回分页所有数据
        return UnResult.ok().data(map);

    }

    //课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public UnResult getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id编写sql语句查询课程的信息
        CourseWebVo courseWebVo=courseService.getBaseCourseInfo(courseId);
        System.out.println(courseWebVo);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVoList=chapterService.getChapterVideoByCourseId(courseId);

        //根据课程Id和用户ID查询订单表中是否支付
        String memberId = JwtUtils.getMemberIdByJwtToken(request);//取出用户id

        //如果id不为空的话就执行，查看订单的状态，并将其传入前端，为空则不做动作
        //TODO 后面网关还会完善这一段代码
         boolean buyCourse=orderVoService.isBuyCourse(courseId, memberId);
        System.out.println(buyCourse);
//            boolean buyCourse = ordersClient.isBuyCourse(courseId, memberId);
         System.out.println(chapterVoList);
        return UnResult.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVoList).data("buyCourse",buyCourse);
    }

    //为了实现支付功能
    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo=courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder=new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return  courseWebVoOrder;
    }



}

