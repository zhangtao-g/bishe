package com.zt.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author ZhangTao
 * @date 2021/4/21 11:42
 * @note:读取配置文件中的内容
 *
 * 用@Value注解将配置文件中相应的值读取到设置的变量中
 *
 * 因为是private类型的不能直接去用，
 * 当项目一启动，就会交给spring管理，下面就会取到值，spring有接口
 *
 *
 */
@Component//交给spring管理
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;



    //定义公开静态额常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    //当上面值初始化的后 接口会执行
    //通过这个公共的方法就可以再别的类中引用这些私有的变凉了
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}
