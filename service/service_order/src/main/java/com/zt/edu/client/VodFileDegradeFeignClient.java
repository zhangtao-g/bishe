package com.zt.edu.client;

import com.zt.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.stereotype.Component;

/**
 * @author ZhangTao
 * @date 2021/5/7 21:45
 *
 *
 * 前面需要配置pom依赖和配置文件
 * @note:熔断机制，若发生熔断则调用此方法
 */

@Deprecated
@Component
public class VodFileDegradeFeignClient implements EduClient {


    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        System.out.println("熔断：出错==>time out");
        return null;
    }
}
