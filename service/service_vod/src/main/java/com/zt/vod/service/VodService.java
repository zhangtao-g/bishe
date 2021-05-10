package com.zt.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/5/5 21:52
 * @note:
 */

@Service
public interface VodService {
    String uploadVideo(MultipartFile viFile);

    void removeAliYunVideo(String id);

    void removeBatchVideo(List videoList);
}
