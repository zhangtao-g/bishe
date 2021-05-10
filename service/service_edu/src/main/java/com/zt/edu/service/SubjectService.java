package com.zt.edu.service;

import com.zt.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-22
 */
public interface SubjectService extends IService<Subject> {

    //添加课程分类
    void saveSubject(MultipartFile file,SubjectService subjectService);
    //课程分类列表树形
    List<OneSubject> getAllOneTwoSubject();
}
