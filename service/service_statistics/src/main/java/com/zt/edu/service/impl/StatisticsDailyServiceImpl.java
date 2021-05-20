package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.commonutils.UnResult;
import com.zt.edu.client.UcenterClient;
import com.zt.edu.entity.StatisticsDaily;
import com.zt.edu.mapper.StatisticsDailyMapper;
import com.zt.edu.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void countRegister(String day) {


//     如果同一天统计的，则先删除在统计
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);
//        int i=1/0;


        UnResult registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)registerR.getData().get("countRegister");

        //获取的数据添加到数据库
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);//注册人数
        sta.setDateCalculated(day);        //注册时间

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);

    }


    /**
     * 图表的显示
     * 对象或者map集合对应的是json的对象形式
     * list集合对应的是json数组
    **/

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {

        //现根据类型，起止日期进行精确查询
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);//查询需要的列，数据时间和类型
        List<StatisticsDaily> lists = baseMapper.selectList(queryWrapper);

        //返回的数据有两部分：date_calculated     和      所类型type
        //  前端是两个数组json，需要json数据，对于java代码的list集合，需要返回x、y轴两个list数据
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> typeList = new ArrayList<>();
        //遍历lists封装两个list集合
        for (StatisticsDaily list:lists){
            dateList.add(list.getDateCalculated());//封装日期

            if (type.equals("login_num")){//封装数量类型
                typeList.add(list.getLoginNum());
            }
            if (type.equals("register_num")){
                typeList.add(list.getRegisterNum());
            }
            if (type.equals("video_view_num")){
                typeList.add(list.getVideoViewNum());
            }
            if (type.equals("course_num")){
                typeList.add(list.getCourseNum());
            }
        }


        //把封住的数据放入map中进行向前端传数据
        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("typeList",typeList);
        return map;
    }
}
