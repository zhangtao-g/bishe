package com.zt.edu.controller;


import com.zt.commonutils.UnResult;
import com.zt.edu.entity.CrmBanner;
import com.zt.edu.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-08
 */
//@CrossOrigin
@RestController
@RequestMapping("/educrm/frontbanner")
public class CrmFrontBannerController {


    @Autowired
    private CrmBannerService crmBannerService;


    //查询所有banner
    @GetMapping("getAllBanner")
    public UnResult getAllBanner(){
     List<CrmBanner> list= crmBannerService.selectAllBanner();
        return UnResult.ok().data("list",list);
    }

}

