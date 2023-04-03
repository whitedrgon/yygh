package com.atguigu.yygh.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author white
 * @since 2023-03-31
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    /*=============================================
       springcache:底层redis、memcache
         1. 导入starter依赖
         2. application.properties: redis连接信息
         3. 在配置类中提供一个cacheManager,在配置类上标记@EnableCaching开启缓存支持注解
         4.@Cacheable(value="")            ：key::value
    ============================================*/

    /**
     * 根据pid查询字典信息  三级联动
     * @param pid
     * @return
     */
    @Override
    public List<Dict> getChildListByPid(Long pid) {
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            //判断该元素下是否有子元素 返回值布尔值
            dict.setHasChildren(isHasChildren(dict.getId()));
        }
        return dicts;
    }

    /**
     * 将数据库表信息写入excel文件  文件下载
     * @param response
     * @throws IOException
     */
    @Override
    public void download(HttpServletResponse response) throws IOException {
        List<Dict> list =baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList = new ArrayList<DictEeVo>(list.size());
        //遍历字典数据集合 下载导出的字典数据不需要全部的Dict属性 使用新的bean对象DictEeVo
        for (Dict dict : list) {
            DictEeVo dictEeVo=new DictEeVo();
            //工具类 将源对象的数据复制到目标对象中
            BeanUtils.copyProperties(dict,dictEeVo);//要求源对象dict和目标对象dictEeVo对应的属性名必须相同
            dictEeVoList.add(dictEeVo);
        }
        //下载：响应头信息
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("字典文件", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("字典数据列表").doWrite(dictEeVoList);
    }

    /**
     * 将excel文件写入内存 再通过调用DictListener存入数据库
     * @param file
     * @throws IOException
     */
    @Override
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet(0).doRead();
    }

    /**
     * 根据pid查询元素个数  数量为0是返回false
     * @param pid
     * @return
     */
    private boolean isHasChildren(Long pid) {
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count >0;
    }
}
