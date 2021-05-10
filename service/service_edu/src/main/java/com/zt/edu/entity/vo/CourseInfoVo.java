package com.zt.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhangTao
 * @date 2021/4/25 14:58
 * @note:此类是为了在课程发布时填写的的课程信息的封装！！！以便传入数据库
 */
@Data
public class CourseInfoVo {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
    @ApiModelProperty(value = "课程二级分类专业ID")
    private String subjectId;
    @ApiModelProperty(value = "课程一级标题")
    private String subjectParentId;//
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;//BigDecimal用来对超过16位有效位的数进行精确的运算
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
}
