package com.elison.platform.user.model.response;

import com.elison.platform.commons.model.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("查询管理用户反馈实体")
public class GetAdminUserResp implements BaseResponse {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("角色ID->角色名称")
    private Map<Long, String> roleMap;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像URL")
    private String headImgUrl;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户状态 0:未激活 1:正常 2:禁用 3:删除")
    private Integer userStatus;
}