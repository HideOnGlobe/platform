package com.elison.platform.user.model.request;

import com.elison.platform.commons.model.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("获取用户微信OpenId请求实体")
public class GetUserWxOpenIdReq implements BaseRequest {

    @NotNull(message = "临时Code不能为空")
    @ApiModelProperty(value = "临时Code")
    private String code;

}