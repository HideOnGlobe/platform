package com.elison.platform.user.exception;

import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.commons.result.ResponseResultBodyAdvice;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.Exception
 * @Description: -ShiroException处理类
 * @Author: elison
 * @CreateDate: 2019/9/7 19:24
 * @UpdateDate: 2019/9/7 19:24
 **/
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShiroException extends ResponseResultBodyAdvice {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public Object incorrectCredentialsException(IncorrectCredentialsException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "密码不正确!"), request);
    }

    @ExceptionHandler(UnknownAccountException.class)
    public Object unknownAccountException(UnknownAccountException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "此账户不存在!"), request);
    }

    @ExceptionHandler(LockedAccountException.class)
    public Object lockedAccountException(LockedAccountException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "此账户已被冻结,请联系管理员!"), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Object unauthorizedException(UnauthorizedException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "权限不足!"), request);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public Object unauthenticatedException(UnauthenticatedException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "无此权限!"), request);
    }

    @ExceptionHandler(AuthorizationException.class)
    public Object authorizationException(AuthorizationException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "权限认证失败!"), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public Object authenticationException(AuthenticationException ex, WebRequest request) {
        return super.exceptionHandler(new ResultException(CodeEnum.FORBIDDEN, "校验失败!"), request);
    }
}
