package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.model.acl.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @PostMapping(value = "/login")
    public R login(@RequestBody User user){
        //暂时不去数据库查:用户系统再去
        return R.ok().data("token","admin-token");
    }

    @GetMapping(value = "/info")
    public R info(String token){
        return R.ok().data("roles","[admin]")
                .data("introduction","I am a super administrator")
                .data("avatar","https://img.zcool.cn/community/01584659ccc891a801218e18e4097e.gif")
                .data("name","Super Admin");
    }

}
