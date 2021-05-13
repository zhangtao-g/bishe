package com.zt.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeahcerFrontList(Page<Teacher> pageTeacher);
}
