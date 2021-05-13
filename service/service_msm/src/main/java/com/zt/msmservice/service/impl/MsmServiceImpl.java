package com.zt.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zt.msmservice.component.HttpUtils;
import com.zt.msmservice.service.MsmService;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/5/10 22:04
 * @note:
 */

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, Map<String, Object> param) {


        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tKSbrjRuatoVSMNs9Fk", "yHTcqZgnWTFxoRGkIxcOxefZ1Zmc1H");
        IAcsClient client = new DefaultAcsClient(profile);

        //固定的
        //配置相关的固定的参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone);//手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");//签名名称
        request.putQueryParameter("TemplateCode", "templateCode");//模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码数据要转为json格式，阿里云要求
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success=response.getHttpResponse().isSuccess();
            return success;
        }  catch (ClientException e) {
            e.printStackTrace();
        }
        return false;




    }
    @Override
    public boolean sendMsm(String  phone, String code){
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "8d99255faf00496888b7663184dff906";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" +code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            if(!StringUtils.isEmpty(response)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
