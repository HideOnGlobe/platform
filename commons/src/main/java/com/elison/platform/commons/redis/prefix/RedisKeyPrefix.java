package com.elison.platform.commons.redis.prefix;

import lombok.AllArgsConstructor;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.redis.prefix
 * @Description: redis-key抽象类,各个模块实现一个模块的抽象子类(固定模块名)
 * 缓存命名规则: MoudleName+Biz/Data+prefix++Key
 * Biz业务缓存:public static final String KEY = new RedisKeyPrefix(moudleName, "").getBizKey();
 * Data数据缓存:public static final String KEY = moudleName + ":" + "data" + ":" + "";
 * @Author: elison
 * @CreateDate: 2020/9/2 17:48
 * @UpdateDate: 2020/9/2 17:48
 **/
@AllArgsConstructor
public class RedisKeyPrefix {

    /**
     * 模块名称
     */
    private String moudleName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 过期时间
     */
    private int expireSeconds;

    public RedisKeyPrefix(String moudleName, String prefix) {
        this(moudleName, prefix, 0);
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public String getBizKey() {
        return moudleName + ":" + "biz" + ":" + prefix;
    }

    public String getBizKey(String suffix) {
        return getBizKey() + ":" + suffix;
    }

    @Deprecated()
    public String getDataKey() {
        // 由于Data类型的Key主要使用在Cache注解中,不准使用方法获取,请使用字段拼接的方式
        return moudleName + ":" + "data" + ":" + prefix;
    }

}
