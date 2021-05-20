package com.zt.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.Teacher;
import com.zt.edu.entity.vo.frontVo.OrderVo;

/**
 * @author ZhangTao
 * @date 2021/5/17 0:31
 * @note:
 */
public interface OrderVoService extends IService<OrderVo> {
    boolean isBuyCourse(String courseId, String memberId);
}
