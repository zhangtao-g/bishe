package com.zt.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.commonutils.UnResult;
import com.zt.edu.entity.Teacher;
import com.zt.edu.entity.vo.TeacherQuery;
import com.zt.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
@CrossOrigin//解决跨域问题

@RestController
//设置请求父路径/edu/teacher
@RequestMapping("/eduservice/teacher")
//@GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class TeacherController {

    @Autowired
    //把service注入
    private TeacherService teacherService;
    //查询讲师列表中所有数据

    @ApiOperation(value = "所有讲师列表 ")
    @GetMapping("findAll")
    //@GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)
    public UnResult findAll(){
        List<Teacher> lists=teacherService.list(null);
        return UnResult.ok().data("items",lists);
    }

//自己写的超简单的逻辑删除，
    @GetMapping("delete")
    public void deleted(){

        teacherService.removeById("1");
    }
    //逻辑删除讲师方法
    // @DeleteMapping("{id}")不能直接用浏览器进行测试，
    //创建公共的模块，整合swagger，为了所有模块都能进行使用
    @ApiOperation(value = "逻辑删除 ")
    @DeleteMapping("{id}")//通过路径传值
    public UnResult remoteTeacher(@ApiParam(name = "id", value = "讲师id",required = true)
                                      @PathVariable String id
                                ){
        Boolean flag =teacherService.removeById(id);
        if (flag){
            return UnResult.ok();
        }else{
            return UnResult.error();
        }

    }
    //逻辑删除讲师方法

//    @GetMapping("{id}")//通过路径传值
//    public boolean remoteTeacher(@PathVariable String id){
//        Boolean flag =teacherService.removeById(id);
//        return flag;
//
//    }

    /**
     * 分页查询讲师的方法
     * current当前页
     * limit每页记录数
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public UnResult PageListTeacher(@PathVariable Long current,
                                    @PathVariable Long limit
    ) {
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current, limit);


        //测试自定义异常
//        try {
//            int b = 10 / 0;//测试异常
//        }catch (Exception e){
//            //执行自定义异常
//            throw new GuliException(20001,"执行了自定义异常处理");
//        }
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页的数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher, null);
        Long total = pageTeacher.getTotal();//总记录数
        List<Teacher> records = pageTeacher.getRecords();//数据list集合

        //可以这样写，也可以用下面这种方法return。
//        Map  map=new HashMap();
//        map.put("total",total);
//        return UnResult.ok().data(map);

        return UnResult.ok().data("total", total).data("rows", records);
    }
    /**
     * 条件组合查询
     *
     * @RequestBody 使用json传递数据，把json数据封装到对应的对象里面
     * 需要post提交方式,required = false参数值可以为空
     * @ResponeBody 返回数据，返回json
     * TeacherQuery teacherQuery  将条件wapper封装在对象中,进行条件查询
     */
    @PostMapping("pageReacherCondition/{current}/{limit}")
    public UnResult pageTeacherCondition(@PathVariable Long current,
                                         @PathVariable Long  limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery
                                    ){//把
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current, limit);

        //构造条件
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        //多条件组合查询
        //与mybatis种动态sql相似
        //判断条件值是否否为空，不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);//等于level
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//大于等于begin
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);//小于等于end
        }
        //调用方法实现查询分页
        wrapper.orderByDesc("gmt_create");//页面显示的时候实现按时间排序
        teacherService.page(pageTeacher,wrapper);
        Long total = pageTeacher.getTotal();//总记录数
        List<Teacher> records = pageTeacher.getRecords();//数据list集合
        return UnResult.ok().data("total", total).data("rows", records);

    }

    /**
     * 添加讲师接口的方法
     *
     * 在测试的时侯id，两个时间值不需要自己加上
     */
    @PostMapping("addTeacher")
    public UnResult addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if(save){
            return UnResult.ok();
        }else{
            return UnResult.error();
        }
    }

    /**
     *根据id取值信息
     * @return
     */
    @GetMapping("getTeacher/{id}")
    public UnResult getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return UnResult.ok().data("teacher",teacher);
    }

    /**
     * 修改信息
     * @param teacher
     * @return
     */
    @PostMapping("updateTeacher")
    public UnResult updateTeacher(@RequestBody Teacher teacher
                                                                ){
        //构造条件
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return UnResult.ok();
        }else{
            return UnResult.error();
        }
    }
    }

