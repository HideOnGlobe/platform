package com.elison.platform.user.model.dao;

import com.elison.platform.commons.model.BaseDO;
import com.elison.platform.user.enums.RoleStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author elison
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysRole对象", description="角色表")
public class SysRole extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父角色(sys_role)ID")
    private Long parentId;

//    @ApiModelProperty(value = "角色代码")
//    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;

    // todo 未启用审核，开启后需要注意关联操作（例如关联的商品在新增时需要判断，此对象是否已经通过了审核，若没有通过审核则不能新增）
    @ApiModelProperty(value = "状态 0：待审核 1：正常 2:禁用 3:删除")
    private RoleStatusEnum roleStatus;


}
