package com.elison.platform.user.redis;


import com.elison.platform.commons.redis.constant.RedisMoudleConstant;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.redis
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 15:14
 * @UpdateDate: 2020/9/9 15:14
 **/
public class UserRedisKeyPrefix {

    private static final String MOUDLE_NAME = RedisMoudleConstant.USER;

    public static final String USER_DATA_SYS_USER_KEY = MOUDLE_NAME + ":" + "data" + ":" + "sys_user";


}
