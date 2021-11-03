package com.atguigu.servicebase.exceptionhandler;


import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)     // 指定出现什么异常执行这个方法
    @ResponseBody                          // 指定返回json格式
    public R error(Exception e) {
        e.printStackTrace();
        return R.ok().message("执行了全局的异常处理......");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody                          // 指定返回json格式
    public R error(ArithmeticException e) {
        return R.error().message("执行了ArithmeticException异常处理.....");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}
