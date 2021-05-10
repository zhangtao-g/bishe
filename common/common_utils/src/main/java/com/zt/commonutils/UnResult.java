package com.zt.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangTao
 * @date 2021/4/9 16:25
 * @note:UnResult统一结果
 */
@Data
public class UnResult {

        @ApiModelProperty(value = "是否成功")
        private Boolean success;
        @ApiModelProperty(value = "返回码")
        private Integer code;
        @ApiModelProperty(value = "返回消息")
        private String message;
        @ApiModelProperty(value = "返回数据")
        private Map<String , Object> data = new HashMap<String, Object>();
          //把构造方法私有
        private UnResult(){};

        //成功的静态方法//私有化构造方法后，该类能不能被实例化，做静态方法，不需要实例化直接调用
        public static UnResult ok(){
            UnResult unResult = new UnResult();
            unResult.setSuccess(true);
            unResult.setCode(ResultCode.SUCCESS);
            unResult.setMessage("成功");
            return unResult;
        }
        //失败的静态方法
    public static UnResult error(){
        UnResult unResult = new UnResult();
        unResult.setSuccess(false);
        unResult.setCode(ResultCode.SUCCESS);
        unResult.setMessage("失败");
        return unResult;
    }
    public UnResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public UnResult message(String message){
        this.setMessage(message);
        return this;
    }
    public UnResult code(Integer code){
        this.setCode(code);
        return this;
    }
    public UnResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public UnResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    }
