package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
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
@ApiModel(value="SysUserWx对象", description="系统用户表")
public class SysUserWx extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户(sys_user)ID")
    private Long userId;

    @ApiModelProperty(value = "wx_open_id")
    private String openId;

    @ApiModelProperty(value = "wx_union_id")
    private String unionId;

}
