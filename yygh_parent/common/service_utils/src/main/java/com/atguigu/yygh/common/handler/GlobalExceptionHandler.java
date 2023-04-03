package com.atguigu.yygh.common.handler;


import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * 全局异常处理类 产生异常会返回前端R对象
 */
//@ControllerAdvice + @ResponseBody =@RestControllerAdvice
@RestControllerAdvice //凡是由@ControllerAdvice标记的类都表示全局异常处理
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class )//粒度：
    public R handleException(Exception ex){
        ex.printStackTrace();//输出异常：实际开发日志文件  此处打印在控制台
        log.error(ex.getMessage());
        return R.error().message(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class )//细粒度的异常处理
    public R handleRuntimeException(RuntimeException ex){
        ex.printStackTrace();//输出异常：实际开发日志文件  此处打印在控制台
        log.error(ex.getMessage());
        return R.error().message("编译时异常");
    }

    @ExceptionHandler(value = SQLException.class )//细粒度的异常处理
    public R handleSqlExcepiton(SQLException ex){
        ex.printStackTrace();//输出异常：实际开发日志文件  此处打印在控制台
        log.error(ex.getMessage());
        return R.error().message("Sql异常");
    }

    @ExceptionHandler(value = ArithmeticException.class )//细粒度的异常处理
    public R handleArithmeticException(ArithmeticException ex){
        ex.printStackTrace();//输出异常：实际开发日志文件  此处打印在控制台
        log.error(ex.getMessage());
        return R.error().message("数学异常");
    }

    //自定义异常类的捕获处理 需要注意 自定义异常需要在异常产生处手动抛出
    @ExceptionHandler(value = YyghException.class )//细粒度的异常处理
    public R handleYyghException(YyghException ex){
        ex.printStackTrace();//输出异常：实际开发日志文件  此处打印在控制台
        log.error(ex.getMessage());
        return R.error().message(ex.getMessage()).code(ex.getCode());
    }
}
