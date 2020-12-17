package com.elison.platform.commons.aspect;

import com.elison.platform.commons.config.GlobeConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.aspect
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/21 23:43
 * @UpdateDate: 2020/9/21 23:43
 **/
@Slf4j
@Aspect
@Component
public class GlobeAspect {

    @Around("execution(public * com.elison.platform.*.controller..*.*(..))")
    public Object controllerLog(ProceedingJoinPoint pdj) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //开始时间
        long startTime = System.currentTimeMillis();

        //获取参数数组
        Object[] args = pdj.getArgs();
        //执行原方法并获取返回结果
        Object ret = pdj.proceed(args);

        //结束时间
        long endTime = System.currentTimeMillis();

        long runTime = endTime - startTime;
        if (runTime >= GlobeConfig.getMaxReqTime()) {
            log.warn("###请求URL: {}  ###IP: {}   ###Params: {}   ###CLASS_METHOD: {}.{}   ###耗时: {}毫秒",
                    request.getRequestURL().toString(), request.getRemoteAddr(), Arrays.toString(args),
                    pdj.getSignature().getDeclaringTypeName(), pdj.getSignature().getName(), runTime);
        } else {
            log.info("###请求URL: {}  ###IP: {}   ###Params: {}   ###CLASS_METHOD: {}.{}   ###耗时: {}毫秒",
                    request.getRequestURL().toString(), request.getRemoteAddr(), Arrays.toString(args),
                    pdj.getSignature().getDeclaringTypeName(), pdj.getSignature().getName(), runTime);
        }

        return ret;
    }
}
