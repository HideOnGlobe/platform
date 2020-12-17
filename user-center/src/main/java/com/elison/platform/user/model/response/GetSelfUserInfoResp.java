package com.elison.platform.user.model.response;

import com.elison.platform.commons.model.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author elison
 */
@Data
@Accessors(chain = true)
@ApiModel("查询个人信息反馈实体")
public class GetSelfUserInfoResp implements BaseResponse {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("权限列表")
    private List<String> permissionList;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像URL")
    private String headImgUrl;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty(value = "状态 0：未激活 1：正常")
    private int userStatus;
}
