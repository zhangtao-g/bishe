package com.zt.edu.entity.vo;

import lombok.Data;

/**
 * @author ZhangTao
 * @date 2021/4/29 17:26
 * @note:封装最终发布的信息
 */

@Data
public class CoursePublishVo {

    private String id;//courseId
    private String title;//title
    private String cover;//课程封面
    private Integer lessonNum;//edu_course
    private String subjectLevelOne;//一级分类subjectLevelOne
    private String subjectLevelTwo;//二级分类subjectLevelTwo
    private String teacherName;
    private String price;//只用于显示


}
