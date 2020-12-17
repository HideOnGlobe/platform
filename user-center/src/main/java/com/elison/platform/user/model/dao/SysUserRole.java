package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户角色关系表
 * </p>
 *
 * @author elison
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysUserRole对象", description="系统用户角色关系表")
public class SysUserRole extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户(sys_user)ID")
    private Long userId;

    @ApiModelProperty(value = "角色(sys_role)ID")
    private Long roleId;


}
