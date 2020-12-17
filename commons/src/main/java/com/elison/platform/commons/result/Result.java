package com.elison.platform.commons.result;

import com.elison.platform.commons.model.BaseResponse;
import com.elison.platform.commons.page.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.result
 * @Description: Controller实际出参包装类
 * @Author: elison
 * @CreateDate: 2020/9/3 15:26
 * @UpdateDate: 2020/9/3 15:26
 **/
@Slf4j
@Data
@Accessors(chain = true)
public class Result implements Serializable {
    private static final long serialVersionUID = -7010755543981539700L;

    private Integer code;

    private Object data;

    private String msg;

    private Result(Integer code, Object data, String msg) {
        if (data == null) {
            this.code = code;
            this.msg = msg;
        } else {
            this.code = code;
            this.data = data;
            this.msg = msg;
        }
    }

    public static Result success(BaseResponse data) {
        return new Result(CodeEnum.SUCCESS.getCode(), data, CodeEnum.SUCCESS.getMsg());
    }

    public static Result success(BaseResponse data, String msg) {
        return new Result(CodeEnum.SUCCESS.getCode(), data, msg);
    }

    public static Result success(PageResult data) {
        return new Result(CodeEnum.SUCCESS.getCode(), data, CodeEnum.SUCCESS.getMsg());
    }

    public static Result success(Collection data) {
        return new Result(CodeEnum.SUCCESS.getCode(), data, CodeEnum.SUCCESS.getMsg());
    }

    public static Result success() {
        return new Result(CodeEnum.SUCCESS.getCode(), null, CodeEnum.SUCCESS.getMsg());
    }

    public static Result success(String msg) {
        return new Result(CodeEnum.SUCCESS.getCode(), null, msg);
    }

    public static Result fail(CodeEnum codeEnum) {
        return fail(codeEnum, codeEnum.getMsg());
    }

    public static Result fail(CodeEnum codeEnum, String msg) {
        return codeEnum != null ? new Result(codeEnum.getCode(), null, msg) : fail(msg);
    }

    public static Result fail() {
        return new Result(CodeEnum.BUSSINESS_ERROR.getCode(), null, CodeEnum.BUSSINESS_ERROR.getMsg());
    }

    public static Result fail(String msg) {
        return (msg != null && msg.length() > 0) ? new Result(CodeEnum.BUSSINESS_ERROR.getCode(), null, msg) : fail();
    }
}
