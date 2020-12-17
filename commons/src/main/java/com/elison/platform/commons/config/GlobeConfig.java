package com.elison.platform.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: monitor
 * @Package: com.elison.platform.commons.config
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/10/10 14:08
 * @UpdateDate: 2020/10/10 14:08
 **/
@Slf4j
@Component
public class GlobeConfig {
    private static Integer transactionalTimes;

    private static Long maxReqTime;

    @Value("${globeConfig.transactionalTimes}")
    private void setTransactionalTimes(String transactionalTimes) {
        GlobeConfig.transactionalTimes = Integer.parseInt(transactionalTimes);
    }

    public static Integer getTransactionalTimes() {
        if (transactionalTimes == null || transactionalTimes <= 0) {
            log.error("后台事务配置异常");
            return 10;
        }
        return transactionalTimes;
    }


    @Value("${globeConfig.maxReqTime}")
    private void setMaxReqTime(String maxReqTime) {
        GlobeConfig.maxReqTime = Long.parseLong(maxReqTime);
    }

    public static Long getMaxReqTime() {
        return maxReqTime;
    }
}
