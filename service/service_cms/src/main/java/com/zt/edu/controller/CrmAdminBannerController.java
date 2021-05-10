package com.zt.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.CrmBanner;
import com.zt.edu.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-08
 */
@CrossOrigin
@RestController
@RequestMapping("/educrm/adminbanner")
public class CrmAdminBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public UnResult pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        return UnResult.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    //添加banner
    @PostMapping("save")
    public UnResult save(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return UnResult.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public UnResult updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return UnResult.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public UnResult remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return UnResult.ok();
    }

}

