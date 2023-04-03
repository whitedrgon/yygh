package com.atguigu.yygh.common.config;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    //配置给admin用户
    @Bean
    public Docket getAdminDocker() {
      return  new Docket(DocumentationType.SWAGGER_2)
              //设置自定义分组admin
              .groupName("admin")
              //设置页面信息
              .apiInfo(getAdminInfo())
              .select()
              //限制admin分组下只能看到以admin开头的接口
              .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
              .build()
              ;
    }
    public ApiInfo getAdminInfo(){
      return   new ApiInfoBuilder()
                .title("管理员系统使用")
                .description("预约挂号平台系统之管理员系统")
                .version("1.0")
                //.contact(new Contact("HYX", "www.hyx.com", "hyx@163.com"))
                .build()
                ;
    }
    //配置user下的接口
    @Bean
    public Docket getUserDocker() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .apiInfo(getUserInfo())
                .select()
                //只想看到user下的接口
                .paths(Predicates.and(PathSelectors.regex("/user/.*")))
                .build()
                ;
    }
    public ApiInfo getUserInfo(){
        return   new ApiInfoBuilder()
                .title("用户系统使用")
                .description("预约挂号平台系统之用户系统")
                .version("1.0")
                //.contact(new Contact("HYX", "www.hyx.com", "hyx@163.com"))
                .build()
                ;
    }
    //配置Api的接口
    @Bean
    public Docket getApiDocker() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("Api")
                .apiInfo(getApiInfo())
                .select()
                //只想看到api下的接口
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build()
                ;
    }
    public ApiInfo getApiInfo(){
        return   new ApiInfoBuilder()
                .title("第三方系统对接")
                .description("预约挂号平台系统之第三方医院对接系统")
                .version("1.0")
                //.contact(new Contact("HYX", "www.hyx.com", "hyx@163.com"))
                .build()
                ;
    }
}
