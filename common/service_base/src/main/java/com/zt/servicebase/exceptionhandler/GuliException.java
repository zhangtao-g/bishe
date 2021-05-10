package com.zt.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhangTao
 * @date 2021/4/12 15:20
 * @note:
 */

@Data
@AllArgsConstructor//有参数构造方法
@NoArgsConstructor//无参数构造
public class GuliException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息
}
