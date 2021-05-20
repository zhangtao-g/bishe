package com.zt.edu.mapper;

import com.zt.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.edu.entity.vo.CoursePublishVo;
import com.zt.edu.entity.vo.frontVo.CourseWebVo;
import com.zt.edu.entity.vo.frontVo.StatusVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
//    public StatusVo isBuy(String courseId, String memberId);

}
