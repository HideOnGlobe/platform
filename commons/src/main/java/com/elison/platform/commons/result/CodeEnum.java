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
    SUCCESS(200, "操作成功!"),

    // 请求异常
    REQ_ERROR(400, "请求异常!"),

    // 服务器异常
    SERVER_ERROR(500, "服务器异常!"),

    // 业务异常
    BUSSINESS_ERROR(1000, "操作失败!"),
    ;

    private final Integer code;
    private final String msg;


    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
