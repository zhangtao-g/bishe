package com.zt.aclservice.controller;


import com.zt.aclservice.entity.Role;
import com.zt.aclservice.entity.User;
import com.zt.aclservice.service.RoleService;
import com.zt.aclservice.service.UserService;
import com.zt.commonutils.MD5;
import com.zt.commonutils.UnResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public UnResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
             User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return UnResult.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }
    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public UnResult get(@PathVariable String id) {
        User user = userService.getById(id);
        return UnResult.ok().data("item", user);
    }
    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public UnResult save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return UnResult.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public UnResult updateById(@RequestBody User user) {
        userService.updateById(user);
        return UnResult.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public UnResult remove(@PathVariable String id) {
        userService.removeById(id);
        return UnResult.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public UnResult batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return UnResult.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public UnResult toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return UnResult.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public UnResult doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return UnResult.ok();
    }
}

