package com.zt.edu.service;

import com.zt.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
public interface VideoService extends IService<Video> {

    //根据课程id删除小节
    void removeVideoBycourseId(String id);

}
