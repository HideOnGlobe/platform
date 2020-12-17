package com.elison.platform.commons.exception;

import com.elison.platform.commons.result.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.result.exception
 * @Description: 业务异常(用于控制接口是否成功)
 * @Author: elison
 * @CreateDate: 2020/9/21 22:41
 * @UpdateDate: 2020/9/21 22:41
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ResultException extends RuntimeException {
    private static final long serialVersionUID = -953457082180197595L;

    private CodeEnum codeEnum;

    private String msg;

    public ResultException(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
        this.msg = codeEnum.getMsg();
    }

    public ResultException(CodeEnum codeEnum, String msg) {
        this.codeEnum = codeEnum;
        this.msg = msg;
    }
}
