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
    public static Long getCurrentUserId() {
        Long userId = null;
        // todo 完善用户Id获取方法
//        Integer uid = (Integer) ApplicationUtils.getRequest().getAttribute(APICons.API_UID);
//        if (Objects.isNull(uid)) {
//            String token = ApplicationUtils.getRequest().getHeader("Authorization");
//            ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, token);
//            token = token.replaceFirst("Bearer ", "");
//            Claims claims = JWTUtils.getClaim(token);
//            ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, claims);
//            return claims.get(JWTUtils.UID, Integer.class);
//        }
        return userId;
    }

    protected static void setCurrentUserId(Long userId) {
        // todo 完善用户Id默认添加方法,由user模块的进行调用
    }

}
