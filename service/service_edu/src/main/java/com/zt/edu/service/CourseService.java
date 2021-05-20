package com.zt.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.vo.CourseInfoVo;
import com.zt.edu.entity.vo.CoursePublishVo;
import com.zt.edu.entity.vo.frontVo.CourseFrontVo;
import com.zt.edu.entity.vo.frontVo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
public interface CourseService extends IService<Course> {


    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

//    boolean isBuyCourse(String courseId, String memberId);
}
