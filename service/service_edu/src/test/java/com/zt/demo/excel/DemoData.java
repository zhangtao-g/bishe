package com.zt.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangTao
 * @date 2021/4/22 11:33
 * @note:测试Easyexcel
 *
 *
 * 读操作的时候还要标记对应列关系
 */
@Data
public class DemoData {


    @ExcelProperty(value = "学生姓名",index = 0)
    private String sname;
    @ExcelProperty(value = "学生编号",index = 1)
    private Integer sno;
}
