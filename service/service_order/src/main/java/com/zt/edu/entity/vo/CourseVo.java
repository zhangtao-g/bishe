package com.zt.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhangTao
 * @date 2021/5/16 17:34
 * @note:
 */

@Data
public class CourseVo {


    private String title;

    private String cover;
    private String name;
    private BigDecimal price;

}
