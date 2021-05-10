package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.edu.entity.CourseDescription;
import com.zt.edu.mapper.CourseDescriptionMapper;
import com.zt.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {


    @Override
    public void removecourseDescriptionBycourseId(String id) {
        QueryWrapper<CourseDescription> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        baseMapper.delete(wrapper);
    }
}
