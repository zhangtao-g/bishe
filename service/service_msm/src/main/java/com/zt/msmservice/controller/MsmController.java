package com.zt.msmservice.controller;

import com.zt.commonutils.UnResult;
import com.zt.msmservice.component.MsmComponent;
import com.zt.msmservice.service.MsmService;
import com.zt.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangTao
 * @date 2021/5/10 22:00
 * @note:已经用阿里云第三方短信服务解决
 */
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {





    @Autowired
    private MsmService msmService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("send/{phone}")
    public UnResult code(@PathVariable String phone) {


        //先从redis中获取，获取不到再随机生成
        String code = redisTemplate.opsForValue().get(phone);
        System.out.println(code);
        if(!StringUtils.isEmpty(code))
            return UnResult.ok();
//      redis中没有   则   随机生成四位验证码
        code = RandomUtil.getFourBitRandom();

//        Map<String,Object> param = new HashMap<>();
//        param.put("code", code);
        System.out.println(code);
        boolean isSend = msmService.sendMsm(phone,code);
        System.out.println(isSend);
        if(isSend) {
            //发送成功吧发送的验证码放到redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return UnResult.ok();
        } else {
            return UnResult.error().message("发送短信失败");
        }
    }

}
