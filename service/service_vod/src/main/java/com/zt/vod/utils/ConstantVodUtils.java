package com.zt.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author ZhangTao
 * @date 2021/5/6 17:47
 * @note:
 */
public class ConstantVodUtils implements InitializingBean {

    @Value("aliyun.vod.file.keyid=")
    private String keyid;
    @Value("aliyun.vod.file.keysecret")
    private String  keysecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;



    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=keyid;
        ACCESS_KEY_SECRET=keysecret;

    }
}
