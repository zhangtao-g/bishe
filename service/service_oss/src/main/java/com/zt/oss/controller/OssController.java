package com.zt.oss.controller;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import com.zt.commonutils.UnResult;
import com.zt.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangTao
 * @date 2021/4/21 12:17
 * @note:
 */
@CrossOrigin
@RestController
@RequestMapping("/eduoss/file")
public class OssController {

    @Autowired
    private OssService ossservice;
    //上传头像的方法
    @PostMapping
    public UnResult uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回上传到oss的路径
        String url = ossservice.uploadFileAvatar(file);
        return UnResult.ok().data("url",url);
    }
}
