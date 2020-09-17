package com.elison.platform.user.service.data;

import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.model.dto.SysUserDTO;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service.data
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 11:06
 * @UpdateDate: 2020/9/9 11:06
 **/
public interface SysPermissionService extends BaseDataService<SysUserDTO> {
    /**
     * 根据角色Id获取权限列表
     * @param roleId 角色Id
     * @return 系统权限列表
     */
    List<SysPermissionDTO> listSysPermissionsByRoleId(Long roleId);
}
