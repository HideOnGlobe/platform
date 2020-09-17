package com.elison.platform.commons.mybatis;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.elison.platform.commons.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 主键生成器
 * @Author: elison
 * @CreateDate: 2020/9/3 18:09
 * @UpdateDate: 2020/9/3 18:09
 **/
@Slf4j
@Component
public class SnowflakeIdGenerator implements IdentifierGenerator {

    @Value("snowflake.dataCenterId")
    private Long dataCenterId;

    private static Long workerId;

    private static final String CHCHE_KEY = "Snowflake:";

    private static final int HASH_NUM = 128;

    private static final long EXPIRE_TIME = 24 * 60 * 60L;

    private static final long CHECK_TIME = 23 * 60 * 60L;

    @Resource
    private RedisUtils redisUtils;

    private static volatile Snowflake snowflake;

    @Override
    public Long nextId(Object entity) {
        if (snowflake == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (snowflake == null) {
                    snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
                }
            }
        }
        return snowflake.nextId();
    }

    @PostConstruct
    private void initSnowflakeWorkerId() {
        String localIp = NetUtil.getLocalhostStr();
        Long ip = Long.parseLong(localIp.replaceAll("\\.", ""));
        Integer tempWorkerId = ip.hashCode() % HASH_NUM;
        int num = 0;
        while (num++ <= HASH_NUM && !redisUtils.setNx(CHCHE_KEY + tempWorkerId.toString(), localIp, EXPIRE_TIME)) {
            if (tempWorkerId++ >= HASH_NUM) {
                tempWorkerId = 0;
            }
        }
        if (num <= HASH_NUM) {
            workerId = tempWorkerId.longValue();
        } else {
            log.error("Snowflake workerId used more than {}, please check project started num!", HASH_NUM);
            System.exit(0);
        }
    }

    private void destroySnowflakeWorkerId() {
        redisUtils.del(CHCHE_KEY + workerId.toString());
    }
}
