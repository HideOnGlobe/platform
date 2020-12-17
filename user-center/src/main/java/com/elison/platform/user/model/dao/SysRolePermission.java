package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色权限关系表
 * </p>
 *
 * @author elison
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysRolePermission对象", description="系统角色权限关系表")
public class SysRolePermission extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色(sys_role)ID")
    private Long roleId;

    @ApiModelProperty(value = "权限(sys_permission)ID")
    private Long permissionId;


}
