package com.atguigu.yygh.cmn.controller;


import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author white
 * @since 2023-03-31
 */
@Api(tags = "字典信息")
@RestController
@RequestMapping("/admin/cmn")
@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;


    @ApiOperation(value = "excel上传")
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        dictService.upload(file);
        System.out.println("我只master主分支" );
        return R.ok();
    }
    @ApiOperation(value = "excel下载")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        dictService.download(response);
    }
    @ApiOperation(value = "根据parent_id查询元素信息")
    @GetMapping("/childList/{pid}")
    public R getChildListByPid(@PathVariable Long pid){
        List<Dict> list=dictService.getChildListByPid(pid);
        return R.ok().data("items",list);
    }
}

