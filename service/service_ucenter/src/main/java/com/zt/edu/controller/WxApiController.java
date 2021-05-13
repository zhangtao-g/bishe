package com.zt.edu.controller;

import com.google.gson.Gson;
import com.zt.commonutils.JwtUtils;
import com.zt.edu.entity.UcenterMember;
import com.zt.edu.service.UcenterMemberService;
import com.zt.edu.utils.HttpClientUtils;
import com.zt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author ZhangTao
 * @date 2021/5/12 11:29
 * @note:
 *
 *
 *
 * 生成的二维码跳转后的界面地址
 * http://localhost:8150/api/ucenter/wx/callback?code=031y7v0w3cXvlW2GvG0w3UOAqY3y7v0Q&state=atguigu
 */

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {



    @Autowired
    private UcenterMemberService memberService;

    //获取扫描人信息，添加数据

    @GetMapping("callback")
    public String callback(String code,String state){

        try {

        //1.获取code值，临时票据，类似于验证码


        //2.拿着code请i求微信得固定的地址，得到两个值assess_id和open_id
        /**
         * System.out.println("accessToken=============" + result);
         * access_token":"45_P-YTwMjNaQWqgBun20v0bns_v0HDnY6TYCV0I8dg72S6z07wCphWNe6BhKfY8TEkgYpk6DhtXe6UubSEIhnbsD8v5IDXK3ukCzrNhvL4H78",
         * "expires_in":7200,
         * "refresh_token":"45_lsYejlC2xudUMPbzFscCJwkRoz7VE8MmvgJt5UDReYghVFkZceNdsJK8wCyhBBPg7Nlw_e3kAS6vzUfKIDsF1En-oLzZ87_88Yyf7vIHarQ",
         * "openid":"o3_SC52qY1EMVGchPoQUIYwz4xyA",
         * "scope":"snsapi_login",
         * "unionid":"oWgGz1AlHUfRx9bRk0g82BaD4zPg"
         * }
         */
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                "wxed9954c01bb89b47",
                "a7482517235173ddb4083788de60b90e",
                code
        );


        //请求这拼接好得地址，得到返回得两个值，token和openid
        //使用httpclient发送请求。得到返回得结果
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);

        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }
        //从tokeninfo中获取出来两个字符token和openid
        //使用tokeninfo字符串转换位map集合，很具mao里面得key获取对应的值
        //使用json转换工具gson
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //3.拿着得到token和openid再去请求微信得固定地址，获去扫描人得信息
        //访问微信得资源服务器，互殴去用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        //拼接两个参数
        String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);

        //发送请求
        String resultUserInfo = null;

            resultUserInfo = HttpClientUtils.get(userInfoUrl);
            System.out.println("resultUserInfo==========" + resultUserInfo);
            /**
             * resultUserInfo=========={
             * "openid":"o3_SC52qY1EMVGchPoQUIYwz4xyA",
             * "nickname":"明天天明",
             * "sex":1,
             * "language":"zh_CN",
             * "city":"Texas",
             * "province":"Shandong",
             * "country":"CN","
             * headimgurl":"https://thirdwx.qlogo.cn/mmopen/vi_32/bGm9tDfNT4CibhbgynsVKSGlF3OQu6cPDnjAiaWmXibJCb7ZDrLJBgibpT6RgiaK2452ibZTyVrDibKfG2EaAVvbcav6w/132",
             * "privilege":[],
             * "unionid":"oWgGz1AlHUfRx9bRk0g82BaD4zPg"}
             */



        //获取上述字符串中得扫描人信息
        HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
        String nickname = (String)mapUserInfo.get("nickname");//昵称
        String headimgurl = (String)mapUserInfo.get("headimgurl");//头像

        //吧扫描人信息添加数据库里面
        //判断数据表中是否存在相同的为微信信息，根据openid进行判断
        UcenterMember member=memberService.getOpenIdMember(openid);
        if(member==null) {
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);//添加
        }

        //用jwt根据member生成token字符
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        //返回首页通过路径传递token
        return "redirect:http://localhost:3000?token=" + token;
        //http://localhost:3000/?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2MjA4MTQ5ODEsImV4cCI6MTYyMDkwMTM4MSwiaWQiOiIxMzkyNDI0MDI5MzQ3OTk1NjUwIiwibmlja25hbWUiOiLmmI7lpKnlpKnmmI4ifQ.WQkcPnST-4lGOVO_zB1D1-Z0QvQNciehJyufS_pUmjM
        } catch (Exception e) {
            throw new GuliException(20001, "获取用户信息失败");
        }


    }

    //1 生成微信扫描二维码

    @GetMapping("login")
    public String getWxCode() {
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = "http://guli.shop/api/ucenter/wx/callback";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                "wxed9954c01bb89b47",
                redirectUrl,
                "zt"
        );

        //重定向到请求微信地址里面
        return "redirect:"+url;
    }
}
