package com.elison.platform.user.model.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.elison.platform.commons.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/4 0:05
 * @UpdateDate: 2020/9/4 0:05
 **/
@Data
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel("系统用户")
@EqualsAndHashCode(callSuper = true)
public class SysUserDO extends BaseDO {

    private static final long serialVersionUID = 9072661169299249654L;

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
}
