package com.elison.platform.user.service.data;

import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service.data
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 10:35
 * @UpdateDate: 2020/9/9 10:35
 **/
public interface SysRoleService extends BaseDataService<SysUserDTO> {
    /**
     * 根据用户Id获取角色列表
     * @param userId 用户Id
     * @return 角色列表
     */
    List<SysRoleDTO> listSysRolesByUserId(Long userId);
}
