package com.zt.edu.mapper;

import com.zt.edu.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.edu.entity.vo.CourseVo;
import com.zt.edu.entity.vo.UcenterVo;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-14
 */
public interface OrderMapper extends BaseMapper<Order> {
    public CourseVo getCourseInfo(String courseId);
    public UcenterVo getUcenterInfo(String courseId);
}
