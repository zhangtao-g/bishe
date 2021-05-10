package com.zt.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/4/24 16:45
 * @note:一级分类
 */
@Data
public class OneSubject {
    private String id;
    private String title;


    //一个一级分类有多个二级分类
    private List<TwoSubject> children =new ArrayList<>();


}
