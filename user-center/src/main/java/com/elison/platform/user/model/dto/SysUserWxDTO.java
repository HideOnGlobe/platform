package com.elison.platform.user.model.dto;

import com.elison.platform.commons.model.BaseDTO;
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
@ApiModel(value="系统用户")
public class SysUserWxDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户(sys_user)ID")
    private Long userId;

    @ApiModelProperty(value = "wx_open_id")
    private String openId;

    @ApiModelProperty(value = "wx_union_id")
    private String unionId;

}
