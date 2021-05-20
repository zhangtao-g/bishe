package com.zt.edu.client;

import com.zt.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ZhangTao
 * @date 2021/5/14 21:02
 * @note:
 */
@Deprecated
@FeignClient(value = "service-edu",fallback = VodFileDegradeFeignClient.class)
@Component
public interface EduClient {


    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);


}
