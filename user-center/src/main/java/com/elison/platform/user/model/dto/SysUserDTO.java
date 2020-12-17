package com.elison.platform.user.model.dto;

import com.elison.platform.commons.model.BaseDTO;
import com.elison.platform.user.enums.UserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.model.dto
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 10:20
 * @UpdateDate: 2020/9/9 10:20
 **/
@Data
@Accessors(chain = true)
@ApiModel("系统用户")
@EqualsAndHashCode(callSuper = true)
public class SysUserDTO extends BaseDTO {
    private static final long serialVersionUID = 4426434002738925526L;

    @ApiModelProperty("登陆名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("用户名")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("头像url")
    private String headImgUrl;

    @ApiModelProperty("最终登录IP地址")
    private String ip;

    @ApiModelProperty(value = "状态 0：未激活 1：正常 2:禁用 3:删除")
    private UserStatusEnum userStatus;

    @ApiModelProperty("角色集合")
    private Set<SysRoleDTO> roleSet = new HashSet<>();

    @ApiModelProperty("权限集合")
    private Set<SysPermissionDTO> permissionSet = new HashSet<>();


}
