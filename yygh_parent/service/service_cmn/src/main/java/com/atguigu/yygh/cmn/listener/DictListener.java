package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * excel文件写入的监听器
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {
    //由于Easyexcel不建议将对象交给spring管理  无法使用依赖注入的方法调用dictMapper
    //使用构造器注入
    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper){
        this.dictMapper=dictMapper;
    }

    //excel读入文件时将每行的数据转化为对应的对象 此处是dictEeVo
    //每解析excel文件中的一行数据，都会调用一次invoke方法
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict=new Dict();
        //源对象dictEeVo数据复制到dict中
        BeanUtils.copyProperties(dictEeVo,dict);
        //执行插入数据之前先查询数据是否已经存在
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<Dict>();
        //主键查询
        queryWrapper.eq("id",dictEeVo.getId());
        Integer count = this.dictMapper.selectCount(queryWrapper);
        if(count > 0){
            //数据已存在 执行更新操作
            this.dictMapper.updateById(dict);
        }else{
            //数据不存在 执行插入操作
            this.dictMapper.insert(dict);
        }
    }

    //当excel文件被解析完毕之后，走这个方法：收尾工作，关闭连接
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    //当解析excel文件某个sheet的标题的时候
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }
}