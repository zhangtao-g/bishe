package com.zt.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.zt.aclservice.entity.Permission;
import com.zt.aclservice.service.IndexService;
import com.zt.aclservice.service.PermissionService;
import com.zt.commonutils.UnResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public UnResult info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return UnResult.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public UnResult getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return UnResult.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public UnResult logout(){
        return UnResult.ok();
    }

}
