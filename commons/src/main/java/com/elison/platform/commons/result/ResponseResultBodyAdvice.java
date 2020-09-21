package com.elison.platform.commons.result;

import com.elison.platform.commons.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.WebUtils;

import java.lang.annotation.Annotation;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.result
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/21 22:21
 * @UpdateDate: 2020/9/21 22:21
 **/
@Slf4j
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ANNOTATION_TYPE) || methodParameter.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof Result) {
            return o;
        } else if (o instanceof BaseResponse) {
            return Result.success((BaseResponse) o);
        } else {
            return o;
        }
    }

    /**
     * 提供对标准Spring MVC异常的处理
     *
     * @param ex      the target exception
     * @param request the current request
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Result<?>> exceptionHandler(Exception ex, WebRequest request) {
        log.error("ExceptionHandler: {}", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof BindException) {
            return this.handleResultException((BindException) ex, headers, request);
        } else if (ex instanceof ResultException) {
            return this.handleResultException((ResultException) ex, headers, request);
        }
        return this.handleException(ex, headers, request);
    }

    /**
     * 对接口校验类返回返回结果的处理
     */
    protected ResponseEntity<Result<?>> handleResultException(BindException ex, HttpHeaders headers, WebRequest request) {
        return this.handleExceptionInternal(ex, Result.fail(CodeEnum.REQ_ERROR,
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()), headers,
                HttpStatus.OK, request);
    }

    /**
     * 对ResultException类返回返回结果的处理
     */
    protected ResponseEntity<Result<?>> handleResultException(ResultException ex, HttpHeaders headers, WebRequest request) {
        return this.handleExceptionInternal(ex, Result.fail(ex.getCodeEnum(), ex.getMsg()), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 异常类的统一处理
     */
    protected ResponseEntity<Result<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {
        return this.handleExceptionInternal(ex, Result.fail(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleExceptionInternal(java.lang.Exception, java.lang.Object, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
     * <p>
     * A single place to customize the response body of all exception types.
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     */
    protected ResponseEntity<Result<?>> handleExceptionInternal(
            Exception ex, Result<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
