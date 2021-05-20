package com.zt.edu.service.impl;

import com.zt.commonutils.ordervo.CourseWebVoOrder;
import com.zt.commonutils.ordervo.UcenterMemberOrder;
import com.zt.edu.client.EduClient;
import com.zt.edu.client.UcenterClient;
import com.zt.edu.entity.Order;
import com.zt.edu.entity.vo.CourseVo;
import com.zt.edu.entity.vo.UcenterVo;
import com.zt.edu.mapper.OrderMapper;
import com.zt.edu.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.edu.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {




    //订单生成
    @Override
    public String createOrders(String courseId, String memberId) {


        //通过sql获取课程信息
        CourseVo courseVo= baseMapper.getCourseInfo(courseId);
        UcenterVo ucenterVo=baseMapper.getUcenterInfo(memberId);
        System.out.println("courseVo"+courseVo);
        System.out.println("uecntero"+ucenterVo);


        //创建Order对象。向Order中设置所需要的数据
        Order order=new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号

        order.setCourseId(courseId);
        order.setCourseTitle(courseVo.getTitle());
        order.setCourseCover(courseVo.getCover());
        order.setTeacherName(courseVo.getName());
        order.setTotalFee(courseVo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterVo.getMobile());
        order.setNickname(ucenterVo.getNickname());


        order.setStatus(0);//支付状态
        order.setPayType(1);//支付类型微信
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
