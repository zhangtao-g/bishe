package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.edu.entity.vo.frontVo.OrderVo;
import com.zt.edu.mapper.OrderVoMapper;
import com.zt.edu.service.OrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@Service
public class OrderVoServiceImpl extends ServiceImpl<OrderVoMapper, OrderVo> implements OrderVoService{

    @Autowired
    private OrderVoService orderVoService;

    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId){
        QueryWrapper<OrderVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = orderVoService.count(queryWrapper);
        if (count>0){//已经支付了
            return true;
        }else {
            return false;
        }
    }

}
