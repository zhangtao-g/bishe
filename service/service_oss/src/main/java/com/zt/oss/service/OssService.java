package com.zt.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangTao
 * @date 2021/4/21 12:18
 * @note:
 */
public interface OssService {
    //上传头像到oss
     String uploadFileAvatar(MultipartFile file);
}
