package com.zt.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/4/22 11:36
 * @note:测试读Easyexcel
 */
public class TestReadEasyExcel {
    public static void main(String[] args) {

        String filename="H:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }

}
