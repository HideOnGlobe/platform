package com.elison.platform.commons.result;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.result
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/21 22:30
 * @UpdateDate: 2020/9/21 22:30
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {
}