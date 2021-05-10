package com.zt.edu.service;

import com.zt.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
public interface ChapterService extends IService<Chapter> {

    //根据课程id查询进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterBycourseId(String id);
}


