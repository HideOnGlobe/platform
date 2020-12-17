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
    SUCCESS(200, "操作成功"),

    // 请求异常
    REQ_ERROR(400, "请求异常"),
    FORBIDDEN(403, "请求拒绝"),

    // 服务器异常
    SERVER_ERROR(500, "服务器异常"),

    // 业务异常
    BUSSINESS_ERROR(1000, "操作失败"),
    GET_ERROR(1001, "查询失败"),
    ADD_ERROR(1002, "新增失败"),
    UPDATE_ERROR(1003, "更新失败"),
    DELETE_ERROR(1004, "删除失败"),

    // 登陆异常
    LOGIN_NOT_ACTIVE_USER(2000, "账号未激活"),
    ACTIVE_AGAIN_USER(2001, "账号已激活，请勿重复激活"),

    SYSTEM_ERROR(9999, "当前系统繁忙，请稍后再试"),
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
