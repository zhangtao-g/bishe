package com.zt.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/4/22 12:03
 * @note:读操作要设置监听器
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行的读取信息，但是不读表头
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("******"+demoData);
    }
    //读取表头信息，里面有方法，复制即可
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
