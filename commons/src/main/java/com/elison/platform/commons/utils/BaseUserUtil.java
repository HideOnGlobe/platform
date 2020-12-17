package com.elison.platform.commons.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.utils
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/16 21:13
 * @UpdateDate: 2020/9/16 21:13
 **/
@Slf4j
public abstract class BaseUserUtil {

    /**
     * 获取当前用户id
     */
    public abstract Long getCurrentUserId();


}
