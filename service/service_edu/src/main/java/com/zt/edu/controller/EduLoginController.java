package com.zt.edu.controller;

import com.zt.commonutils.UnResult;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhangTao
 * @date 2021/4/18 20:29
 * @note:
 */

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin//解决跨域问题
public class EduLoginController {
    //login
    @PostMapping("login")
    public UnResult login(){
        return UnResult.ok().data("token","admin");
    }


    //info
    @GetMapping("info")
    public UnResult info(){
        return UnResult.ok().data("roles","admin").data("name","admin").data("avatar","https://edu-zt-001.oss-cn-beijing.aliyuncs.com/xm.jpg");
    }
}
