package com.zt.aclservice.controller;


import com.zt.aclservice.entity.Permission;
import com.zt.aclservice.service.PermissionService;
import com.zt.commonutils.UnResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public UnResult indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        System.out.println(list);
        return UnResult.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public UnResult remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return UnResult.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public UnResult doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return UnResult.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public UnResult toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return UnResult.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public UnResult save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return UnResult.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public UnResult updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return UnResult.ok();
    }

}

