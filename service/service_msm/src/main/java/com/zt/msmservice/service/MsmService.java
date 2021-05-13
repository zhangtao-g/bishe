package com.zt.msmservice.service;

import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/5/10 22:04
 * @note:
 */
public interface MsmService {
    boolean send(String phone, Map<String, Object> param);
     boolean sendMsm(String  phone, String code);
}
