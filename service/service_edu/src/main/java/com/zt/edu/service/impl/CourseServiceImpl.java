package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.deploy.xml.GeneralEntity;
import com.zt.edu.entity.Course;
import com.zt.edu.entity.CourseDescription;
import com.zt.edu.entity.vo.CourseInfoVo;
import com.zt.edu.entity.vo.CoursePublishVo;
import com.zt.edu.entity.vo.frontVo.CourseFrontVo;
import com.zt.edu.entity.vo.frontVo.CourseWebVo;
import com.zt.edu.entity.vo.frontVo.StatusVo;
import com.zt.edu.mapper.CourseMapper;
import com.zt.edu.service.ChapterService;
import com.zt.edu.service.CourseDescriptionService;
import com.zt.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.edu.service.VideoService;
import com.zt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    //添加课程的具体方法
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    /**
     * 问题描述：初试有个问题就是，添加的时候，添加到课程中的数据的id与描述信息的id是没有关系的，
     *          这两个表是一对一的关系，id值应该相等
     * 解决：1.获取添加之后的课程的id
     *      2.从课程表述中添加课程的id，使之两个id一样
     *
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //1.向课程表中添加信息
        //将courseinfovo中的信息转换为course
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert=baseMapper.insert(course);
        if(insert==0){
            //添加失败
            throw new GuliException(20001,"课程添加失败");
        }
        //获取添加之后的课程的id
        String cid=course.getId();


        //2.向课程简介表中添加课程简介
        CourseDescription courseDescription=new CourseDescription();
        //添加课程的id，使之两个id一样
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return  cid;

    }


    /**
     *
     * @param courseId
     * @return courseInfoVo
     *
     * 因为前端中填写课程基本信息，创建了一个courseInfoVO对象，
     * 进行回显的时候就要将根据这个courseid在course和coursedescription表中获取相关数据写回到之前封装好的对象中
     *
     *
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        Course course=baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        //查询描述表
        CourseDescription courseDescription=courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());


        return courseInfoVo;
    }
    //数据回显的时候进行修改信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
       int insert= baseMapper.updateById(course);
       if(insert==0){
           throw new GuliException(20001,"修改课程信息失败");
       }
        //修改描述表
        CourseDescription courseDescription=new CourseDescription();
       courseDescription.setId(courseInfoVo.getId());
       courseDescription.setDescription(courseDescription.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    @Override
    public CoursePublishVo publishCourseInfo( String id) {
        //调用mapper
       CoursePublishVo coursePublishVo= baseMapper.getPublishCourseInfo(id);
        return coursePublishVo;
    }


    /**
     * 这是在courseService中，删除相应的部分要去相应的impl中实现
     * 所以得注入相应得service
     * @param id
     */
    @Override

    public void removeCourse(String id) {
        //删除小节
        //TODO 删除小节时删除对应得视频文件
        videoService.removeVideoBycourseId(id);

        //删除章节
        chapterService.removeChapterBycourseId(id);

        //删除课程描述
        courseDescriptionService.removecourseDescriptionBycourseId(id);
        //删除课程

        int result=baseMapper.deleteById(id);
        if (result==0){//失败返回
            throw  new GuliException(20001,"删除失败");
        }
    }

//    条件查询
    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam, CourseFrontVo courseQuery) {
        //根据讲师id查询所有课程
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

//    @Override
//    public boolean isBuyCourse(String courseId, String memberId) {
//
//      StatusVo vo = baseMapper.isBuy(courseId,memberId);
//        System.out.println(vo.getStatus());
//        System.out.println(vo.getStatus().toString());
//        if(vo.getStatus().toString()=="1"){
//            return true;
//        }
//        return false;
//    }


}
