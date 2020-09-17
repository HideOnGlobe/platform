package com.elison.platform.commons.result;

import com.elison.platform.commons.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.result
 * @Description: Controller实际出参包装类
 * @Author: elison
 * @CreateDate: 2020/9/3 15:26
 * @UpdateDate: 2020/9/3 15:26
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Result<T extends BaseResponse> implements Serializable {
    private static final long serialVersionUID = -7010755543981539700L;
    private static final String SUCCESS_MSG = "操作成功!";
    private static final String FAIL_MSG = "操作失败!";

    private Integer code;

    private T data;

    private String msg;

    public static Result success(BaseResponse data) {
        return new Result<>(CodeEnum.SUCCESS.getCode(), data, SUCCESS_MSG);
    }

    public static Result success(BaseResponse data, String msg) {
        return new Result<>(CodeEnum.SUCCESS.getCode(), data, msg);
    }

    public static Result success() {
        return new Result<>(CodeEnum.SUCCESS.getCode(), null, SUCCESS_MSG);
    }

    public static Result success(String msg) {
        return new Result<>(CodeEnum.SUCCESS.getCode(), null, msg);
    }

    public static Result fail(CodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), null, FAIL_MSG);
    }

    public static Result fail(CodeEnum codeEnum, String msg) {
        return new Result<>(codeEnum.getCode(), null, msg);
    }

    public static Result fail() {
        return new Result<>(CodeEnum.BUSSINESS_ERROR.getCode(), null, FAIL_MSG);
    }

    public static Result fail(String msg) {
        return new Result<>(CodeEnum.BUSSINESS_ERROR.getCode(), null, msg);
    }
}
