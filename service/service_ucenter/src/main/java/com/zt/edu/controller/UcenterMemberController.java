package com.zt.edu.controller;


import com.zt.commonutils.JwtUtils;
import com.zt.commonutils.UnResult;
import com.zt.commonutils.ordervo.UcenterMemberOrder;
import com.zt.edu.entity.UcenterMember;
import com.zt.edu.entity.vo.RegisterVo;
import com.zt.edu.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.rmi.server.UnicastRemoteObject;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/educenter/ucenter")
public class UcenterMemberController {


    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public UnResult loginUser(@RequestBody UcenterMember member){
        //调用service实现登录
        //返回token。用jwt生成
        System.out.println(member);
      String token=  ucenterMemberService.login(member);

        return UnResult.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public UnResult reginterUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return UnResult.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public UnResult getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法。根据request对象获取头信息，返回id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember=ucenterMemberService.getById(memberId);
        return UnResult.ok().data("userInfo",ucenterMember);
    }

    /**
     * 支付功能
     * 根据用户id获取用户信息
     */
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member=ucenterMemberService.getById(id);
        //把member对象里的值传入新建的UcenterMemberOrder里
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);

        return ucenterMemberOrder;
    }

    //查询某一天的人数
    @GetMapping("countRegister/{day}")
    public UnResult countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.ucenterMemberService(day);
        return UnResult.ok().data("countRegister",count);
    }

}

