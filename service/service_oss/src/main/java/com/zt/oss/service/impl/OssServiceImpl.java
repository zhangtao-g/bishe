package com.zt.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zt.oss.service.OssService;
import com.zt.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author ZhangTao
 * @date 2021/4/21 12:18
 * @note:
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //用工具类获取
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取文件上传输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称

            String fileName = file.getOriginalFilename();
            ///当文件名重复的时候，再oss中会覆盖掉原有的文件

            //解决1：用uuid---------------replace是为了将随机生成的数字的“-”去掉
            String uuid= UUID.randomUUID().toString().replace("-","");
//            //拼接
             fileName=uuid+fileName;

             //解决2：把文件按日期分类
            //2021//11/12/01.jpg
            //获取当前日期，用依赖中的工具类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName=datePath+fileName;

            //调用oss方法实现上传
            //参数1：Bucket名称
            //参数2：上传到oss的文件路径和文件名
            //参数3：上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后的文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //根据路径名‘
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
