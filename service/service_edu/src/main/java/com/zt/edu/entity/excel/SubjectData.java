package com.zt.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangTao
 * @date 2021/4/22 15:31
 * @note:创建和excel对应的实体类
 *
 * 就是excel中的表头一级分类，二级分类中的列
 */

@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
