package com.elison.platform.commons.result;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.web
 * @Description: 接口状态码枚举类
 * @Author: elison
 * @CreateDate: 2020/9/3 15:46
 * @UpdateDate: 2020/9/3 15:46
 **/
public enum CodeEnum {

    // 正常状态
    SUCCESS(200),

    // 请求异常
    REQ_ERROR(400),

    // 服务器异常
    SERVER_ERROR(500),

    // 业务异常
    BUSSINESS_ERROR(1000),
    ;

    private final Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
