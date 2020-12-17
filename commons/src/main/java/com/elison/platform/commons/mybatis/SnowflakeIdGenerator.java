package com.elison.platform.commons.mybatis;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

    private Long dataCenterId;

    private static Long workerId;

    private static final String CHCHE_KEY = "Snowflake:";

    private static final String SPLIT = ":";

    private static final int HASH_NUM = 31;

    private static final long EXPIRE_TIME = 24 * 60 * 60L;

    private static final long CHECK_TIME = 23 * 60 * 60L;

    private static final String startTime = "2020-01-01 00:00:00.000";

//    @Autowired
//    private RedisUtils redisUtils;

    private static volatile Snowflake snowflake;

    @Value("${snowflake.dataCenterId}")
    private void setDataCenterId(String dataCenterId) {
        this.dataCenterId = Long.parseLong(dataCenterId);
    }

    @Override
    public Long nextId(Object entity) {
        if (snowflake == null) {
            return null;
        }
        return snowflake.nextId();
    }

    @PostConstruct
    private void initSnowflakeWorkerId() {
//        String localIp = NetUtil.getLocalhostStr();
//        Long ip = Long.parseLong(localIp.replaceAll("\\.", ""));
//        Integer tempWorkerId = ip.hashCode() % HASH_NUM;
//        int num = 0;
//        while (num++ <= HASH_NUM
//                && !redisUtils.setNx(CHCHE_KEY + dataCenterId + SPLIT + tempWorkerId.toString(), localIp, EXPIRE_TIME)) {
//            if (tempWorkerId++ >= HASH_NUM) {
//                tempWorkerId = 0;
//            }
//        }
//        if (num <= HASH_NUM) {
//            workerId = tempWorkerId.longValue();
//            snowflake = new Snowflake(DateUtil.parseByPatterns(startTime, "yyyy-MM-dd HH:mm:ss.SSS").getTime(),
//                    workerId, dataCenterId, false);
//
//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    // (已存在并非本方 || 重新set失败) 则 视为雪花锁占位符校验失败
//                    if ((redisUtils.hasKey(CHCHE_KEY + dataCenterId + SPLIT + workerId.toString())
//                            && !localIp.equals(redisUtils.get(CHCHE_KEY + dataCenterId + SPLIT + workerId.toString())))
//                            || !redisUtils.set(CHCHE_KEY + dataCenterId + SPLIT + workerId.toString(), localIp, EXPIRE_TIME)) {
//                        log.error("Snowflake workerId be used other machine, please check Cache Info!");
//                        snowflake = null;
////                        System.exit(0);
//                    }
//                }
//            }, new Date(), CHECK_TIME);
//        } else {
//            log.error("Snowflake workerId used more than {}, please check project started num!", HASH_NUM);
////            System.exit(0);
//        }
    }


    @PreDestroy
    private void destroySnowflakeWorkerId() {
//        redisUtils.del(CHCHE_KEY + dataCenterId + SPLIT + workerId.toString());
    }
}
