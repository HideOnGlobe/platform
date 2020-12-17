package com.elison.platform.commons.redis.prefix;

import com.elison.platform.commons.redis.constant.RedisMoudleConstant;

/**
 * @ProjectName: monitor
 * @Package: com.elison.platform.commons.redis.constant
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/28 09:04
 * @UpdateDate: 2020/9/28 09:04
 **/
public class CommonRedisKeyPrefix {

    private static final String moudleName = RedisMoudleConstant.COMMON;

    public static final String COMMON_BIZ_CODE_KEY = moudleName + ":" + "biz" + ":" + "code_";
}
