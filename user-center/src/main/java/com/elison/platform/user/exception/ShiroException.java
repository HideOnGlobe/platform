package com.elison.platform.user.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.Exception
 * @Description: -ShiroException处理类
 * @Author: elison
 * @CreateDate: 2019/9/7 19:24
 * @UpdateDate: 2019/9/7 19:24
 **/
@ControllerAdvice
public class ShiroException {
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(UnauthorizedException e) {
        return "403";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(AuthorizationException e) {
        return "403";
    }
}
