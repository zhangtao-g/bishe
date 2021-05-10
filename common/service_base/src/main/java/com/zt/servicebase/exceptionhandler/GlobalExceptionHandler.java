package com.zt.servicebase.exceptionhandler;

import com.zt.commonutils.UnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.UnrecoverableEntryException;

/**
 * @author ZhangTao
 * @date 2021/4/11 9:28
 * @note:统一异常处理类
 */
@Slf4j
@ControllerAdvice//@ControllerAdvice来声明一些全局性的东西，
// 最常见的是结合@ExceptionHandler注解用于全局异常的处理。

public class GlobalExceptionHandler {



    //指定了出现什么异常执行这个方法
    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public UnResult error(Exception e){
        e.printStackTrace();
        return UnResult.error().message("执行了全局异常处理");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public UnResult error(ArithmeticException e){
        e.printStackTrace();
        return UnResult.error().message("执行了ArithmeticException全局异常处理");
    }


    /**
     *1.在exceptionhandler中创建自定义异常GuliException
     *2.自定义异常处理GuliExceptionHandler
     *3.异常需要将异常用try捕获
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody//为了返回数据
    public UnResult error(GuliException e){
        log.error(e.getMessage());//将自定义异常信息写到error文件中
        e.printStackTrace();
        return UnResult.error().code(e.getCode()).message(e.getMsg());
    }

}


