package com.zt.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangTao
 * @date 2021/4/22 11:36
 * @note:
 */
public class TestWriteEasyExcel {
    public static void main(String[] args) {
        //实现excel的写操作
        //设置写入的文件夹地址和excel文件名，
        String filename="H:\\write.xlsx";
        //调用easyexcel中的方法是实现写操作，
        //文件流自动关闭
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }
    //创建一个方法返回list集合

    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData demoData=new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            list.add(demoData);
        }
        return list;
    }
}
