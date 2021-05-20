package com.zt.edu.service;

import com.zt.edu.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-17
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegister(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
