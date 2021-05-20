package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Chapter;
import com.zt.edu.entity.chapter.ChapterVo;
import com.zt.edu.service.ChapterService;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-25
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;



    @GetMapping("getChapterVideo/{courseId}")
    public UnResult getChapterVideo(@PathVariable String courseId){
     List<ChapterVo> list=   chapterService.getChapterVideoByCourseId(courseId);


     return UnResult.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public UnResult addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return UnResult.ok();
    }

    //根据id查询//数据回显
    @GetMapping("getChapter/{chapterId}")
    public UnResult getChapterInfo(@PathVariable String chapterId){
        Chapter chapter=chapterService.getById(chapterId);
        return UnResult.ok().data("chapter",chapter);
    }

    //修改
    @PostMapping("updateChapter")
    public UnResult updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return UnResult.ok();
    }

    //删除

    //删除的时候章节中有小节
    //  1.章节中小节一并删除，联级删除
    //  2.有小节的话不能删除，限制删除
    @DeleteMapping("{chapterId}")
    public UnResult deletedChapter(@PathVariable String chapterId){
       boolean flag= chapterService.deleteChapter(chapterId);
       if(flag){

           return UnResult.ok();
       }else{
           return UnResult.error();
       }
    }

}

