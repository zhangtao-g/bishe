package com.zt.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ZhangTao
 * @date 2021/5/16 17:35
 * @note:
 */
@Data
public class UcenterVo {

    @ApiModelProperty(value = "手机号")
    private String mobile;


    @ApiModelProperty(value = "昵称")
    private String nickname;
}
