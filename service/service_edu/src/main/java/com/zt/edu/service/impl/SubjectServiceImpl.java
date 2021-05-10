package com.zt.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zt.edu.entity.Subject;
import com.zt.edu.entity.excel.SubjectData;
import com.zt.edu.entity.subject.OneSubject;
import com.zt.edu.entity.subject.TwoSubject;
import com.zt.edu.listener.SubjectExcelListener;
import com.zt.edu.mapper.SubjectMapper;
import com.zt.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.edu.service.VideoService;
import jdk.internal.util.xml.impl.Input;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-22
 *
 * 这就是用Easyexcel来读取controller传过来的excel文件
 *
 *
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {



    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            //文件输入流
            InputStream in=file.getInputStream();
            //调用方法进行读取
            //吧service直接注入进来为了后面能使用

            //因为在listener中不能注入service所以在这个serviceiimpl中，通过listener使service注入进去，为了在listener中能够使用service中的发方法save/
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1.查询一级分类 parent_id=0
        QueryWrapper<Subject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);//eq等于
        List<Subject> oneSubjectList=baseMapper.selectList(wrapperOne);
//        System.out.println(oneSubjectList);//[Subject(id=1386140928145620993, title=前端, parentId=0, sort=0, gmtCreate=Sun Apr 25 10:12:05 CST 202........]

        //2.查询二级分类parent_id!=0
        QueryWrapper<Subject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);//ne不等于
        List<Subject> twoSubjectList=baseMapper.selectList(wrapperTwo);
//        System.out.println(twoSubjectList);//集合类型：[Subject(id=1386140928384696322, title=Java, parentId=1386140928145620993, sort=0, gmtCreate=Sun Apr 25 10:12:05......]

        //创建list集合,只有id和title，用于存储最终的封装数据，先储存一级分类
        List<OneSubject> finalSubjectList=new ArrayList<>();


 //         一级二级取出来都放到Subject中
 //  ==========================================================================
        //3.封装一级分类
        //查询出来的所有一级分类List集合遍历，得到每个一级分类的对象，获取每个一级分类
        //封装到要求list集合里面List<OneSubject> findSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //把集合中的值存到对象里
            Subject subject=oneSubjectList.get(i);//一条一条的写进去是为了，写入对应的二级分类
//            System.out.println(subject);//对象：Subject(id=1386140928145620993, title=前端, parentId=0, sort=0, gmtCreate=Sun Apr 25 10:12:05 CST 2021,........
            //把Subject中的id和title值获取出来，放到OneSubject对象里面
            //多个onesubject对象放到finalsubject中去
            OneSubject oneSubject=new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            //使用工具类,就是将subject中get，在onesubject中set
            BeanUtils.copyProperties(subject,oneSubject);
            //多个OneSubject放到finalSubject中去
//            System.out.println(oneSubject);//OneSubject(id=1385867919895908353, title=前端, children=[])
            finalSubjectList.add(oneSubject);

            //在一级分类中循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectsList =new ArrayList<>();

            //遍历二级分类的list
            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每个二级分类
                Subject tSubject=twoSubjectList.get(j);//为了比较找出同一一级分类的
                //判断二级分类的parent_id值是否等于和一级分类的id值是否一样
                if(tSubject.getParentId().equals(subject.getId())){
                    //把tSubject的值放到twoSubject中，最后放到fianlsubject中

                    TwoSubject twoSubject=new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectsList.add(twoSubject);
                }

            }
            //把一级下面的所有二级分类放到一级分类中
            oneSubject.setChildren(twoFinalSubjectsList);
        }

        return finalSubjectList;
    }
}
