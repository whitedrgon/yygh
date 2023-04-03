package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author hyx
 * @since 2023-03-20
 */
@RestController
@Api(tags = "医院设置信息")
@RequestMapping("/admin/hosp/hospitalSet")
@Slf4j //打印日志
@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    //springboot默认只会输出日志的级别：info: debug < info <warn <error
    //logback springboot自带的日志输出 配合slf4j可以输出信息到日志文件中
    //锁定与解锁医院状态
    @ApiOperation(value = "锁定与解锁医院状态接口")
    @PutMapping("/status/{id}/{status}")
    public R updateStatus(@PathVariable Long id,@PathVariable Integer status){
        log.info("current thread is "+Thread.currentThread().getId()+",params:id="+id);//这句话会输出到日志文件中
        HospitalSet hospitalSet=new HospitalSet();
        hospitalSet.setId(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        //HospitalSet byId = hospitalSetService.getById(id); //使用乐观锁时
        log.info("result "+Thread.currentThread().getId()+hospitalSet.toString());//这句话会输出到日志文件中
        return R.ok();
    }
    //批量删除
    @ApiOperation(value = "批量删除医院信息接口")
    @DeleteMapping("/delete")
    public R batchDelete(@RequestBody List<Integer> ids){
        hospitalSetService.removeByIds(ids);
        return R.ok();
    }

    //修改之回显数据
    @ApiOperation(value = "修改之回显医院信息的接口")
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Integer id){
        return R.ok().data("item",hospitalSetService.getById(id));
    }
    //修改之修改数据
    @ApiOperation(value = "修改之根据医院编号修改信息的接口")
    @PutMapping("/update")
    public R update(@RequestBody HospitalSet hospitalSet){
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation(value = "新增医院信息的接口")
    @PostMapping("/save")
    public R save(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //当前时间戳+随机数+MD5加密
        Random random=new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + ""
                + random.nextInt(1000)));
        //新增数据
        hospitalSetService.save(hospitalSet);
        return R.ok();
    }

    /**
     * 带查询条件的分页
     * @param pageNum
     * @param size
     * @param hospitalSetQueryVo
     * @return
     */
    @ApiOperation(value = "带查询条件的分页")
    @PostMapping(value = "/page/{pageNum}/{size}")
    public R getPageInfo(@PathVariable Integer pageNum,
                         @PathVariable Integer size,
                         @RequestBody HospitalSetQueryVo hospitalSetQueryVo){
        //hospitalSetQueryVo  VO对象(与POJO对象类似) 前端与controller层传值使用的对象 过滤敏感信息
        Page<HospitalSet> page1=new Page<>(pageNum,size);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        //两个模糊查询条件的非空判断
        if(!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())){
            queryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())){
            queryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }
        //带条件的分页查询
        hospitalSetService.page(page1, queryWrapper);
        //返回总条数和信息集合
        return R.ok().data("total",page1.getTotal()).data("rows",page1.getRecords());

    }

    //使用REST风格的请求方式 ，在请求中传递参数
    //查询所有接入医院列表
    @ApiOperation(value = "查询所有的医院设置信息")
    @GetMapping("/findAll")
    public R findAll(){
        List<HospitalSet> list = hospitalSetService.list();
        return R.ok().data("items", list);
    }

    //根据医院的id删除医院的信息
    //在Bean 中 类上该字段 加了@TableLogic 注解 （逻辑删除，实际不会删除，只会改变字段数值）
    //@PathVariable 映射 URL 绑定的占位符
    @ApiOperation(value = "根据医院id删除医院")
    @DeleteMapping(value = "/deleteById/{id}")
    public R deleteById(@PathVariable Integer id) {
        hospitalSetService.removeById(id);
        return R.ok();
    }
}
//string: prefix +string+suffix.html:PC
//项目 json格式：PC+物手机+联网

//boolean:


//List<>
    /*=============================================
          @Api(tags=""):标记在接口类上
          @ApiOperation(value=""):标记在方法上
          @ApiParam(value=""):标记在参数上

          @ApiModel(description=")：对POJO类做说明
          @ApiModelProperty(value=")：对POJO类属性做说明

      ============================================*/

