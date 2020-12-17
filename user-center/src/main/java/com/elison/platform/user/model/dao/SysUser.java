package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
import com.elison.platform.user.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author elison
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登陆名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ApiModelProperty(value = "用户名")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "头像url")
    private String headImgUrl;

    @ApiModelProperty(value = "最终登录IP地址")
    private String ip;

    @ApiModelProperty(value = "状态 0：未激活 1：正常 2:禁用 3:删除")
    private UserStatusEnum userStatus;

}
