package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author elison
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysPermission对象", description="系统权限表")
public class SysPermission extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "权限名称")
    private String name;


}
