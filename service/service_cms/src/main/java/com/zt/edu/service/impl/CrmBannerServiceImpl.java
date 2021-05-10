package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.edu.entity.CrmBanner;
import com.zt.edu.mapper.CrmBannerMapper;
import com.zt.edu.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner

    /**
     * 根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，
     * 则直接读取缓存数据返回；如果缓存不存在，则执行方法，并把
     * 返回的结果存入缓存中。一般用在查询方法上。
     * @return
     */
    @Cacheable(value = "banner",key="'selectIndexList'")
    @Override
    public  List<CrmBanner> selectAllBanner() {

        //根据id进行降序排序，显示排列之后的前两条记录

        QueryWrapper <CrmBanner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接语句
        wrapper.last("limit 2");
        List<CrmBanner> list=baseMapper.selectList(wrapper );
        return list;
    }
}
