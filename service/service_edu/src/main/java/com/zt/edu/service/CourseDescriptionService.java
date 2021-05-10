package com.zt.edu.service;

import com.zt.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

    void removecourseDescriptionBycourseId(String id);

}
