package com.elison.platform.user.redis;

import com.elison.platform.commons.redis.constant.RedisMoudleConstant;
import com.elison.platform.commons.redis.prefix.RedisKeyPrefix;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.redis
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 15:14
 * @UpdateDate: 2020/9/9 15:14
 **/
public class UserRedisKeyPrefix {

    private static final String moudleName = RedisMoudleConstant.USER;

    public static final String USER_DATA_SYS_USER_KEY = moudleName + ":" + "data" + ":" + "sys_user";

    public static final String USER_BIZ_SYS_USER_KEY = new RedisKeyPrefix(moudleName, "sys_user").getBizKey();

}
