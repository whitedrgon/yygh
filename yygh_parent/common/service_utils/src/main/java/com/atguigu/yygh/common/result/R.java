package com.atguigu.yygh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data //lombok注解 可以省略get set方法
public class R {
    private Integer code;
    private Boolean success;
    private String message;
    //在这需要将map直接new出来，防止后面出现空指针异常
    private Map<String,Object> data = new HashMap<String,Object>();
    //构造器私有化
    private R() {}

    // ok 返回R对象 支持链式调用
    //采用枚举类REnum优化代码规范
    public static R ok() {
        R r = new R();
        r.code=REnum.SUCCESS.getCode();
        r.success=REnum.SUCCESS.getFlag();
        r.message = REnum.SUCCESS.getMessage();
        return r;
    }
    //error
    public static R error() {
        R r = new R();
        r.code = REnum.ERROR.getCode();
        r.message = REnum.ERROR.getMessage();
        r.success = REnum.ERROR.getFlag();
        return r;
    }

    public R code(Integer code) {
        this.code = code;
        return this;
    }
    public R success(Boolean success) {
        this.success = success;
        return this;
    }

    public R message(String message) {
        this.message = message;
        return this;
    }
    public R data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.data = map;
        return this;
    }
}
