/**
 * 文件名称：GlobalExceptionHandler.java
 * 描述：全局异常处理器，统一返回错误响应
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.common.Result
 */
package com.electronic.api.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        String message = e.getMessage();
        int code = 500;
        if (message != null) {
            if (message.contains("未登录") || message.contains("登录已过期")) {
                code = 401;
            } else if (message.contains("无权")) {
                code = 403;
            } else if (message.contains("不存在")) {
                code = 404;
            } else if (message.contains("已注册") || message.contains("格式不正确")
                    || message.contains("不能少于") || message.contains("库存不足")
                    || message.contains("已下架") || message.contains("密码错误")
                    || message.contains("已被禁用")) {
                code = 400;
            }
        }
        return Result.error(code, message);
    }
}
// AI-GENERATED-END
