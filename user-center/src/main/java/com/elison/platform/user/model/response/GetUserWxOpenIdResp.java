package com.elison.platform.user.model.response;

import com.elison.platform.commons.model.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("获取微信用户OpenId反馈实体")
public class GetUserWxOpenIdResp implements BaseResponse {

    @ApiModelProperty("openid")
    private String openId;

}