package com.zt.edu.service;

import com.zt.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.vo.CourseInfoVo;
import com.zt.edu.entity.vo.CoursePublishVo;

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
}
