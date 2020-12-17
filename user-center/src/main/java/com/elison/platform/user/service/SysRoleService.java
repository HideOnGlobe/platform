package com.elison.platform.user.service;


import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dao.SysRole;
import com.elison.platform.user.model.dto.SysRoleDTO;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service.data
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 10:35
 * @UpdateDate: 2020/9/9 10:35
 **/
public interface SysRoleService extends BaseDataService<SysRoleDTO, SysRole> {
    /**
     * 根据用户Id获取角色列表
     *
     * @param userId 用户Id
     * @return 角色列表
     */
    List<SysRoleDTO> listSysRolesByUserId(Long userId);

    /**
     * 角色名称是否已存在
     *
     * @param roleName 角色名称
     * @return 是否存在
     */
    boolean containsRoleName(String roleName);

    /**
     * 新增角色
     *
     * @param sysRoleDTO      角色信息
     * @param permissionIdSet 角色权限信息
     * @return 是否成功
     */
    boolean insert(SysRoleDTO sysRoleDTO, Set<Long> permissionIdSet);

    /**
     * 更新角色
     *
     * @param sysRoleDTO      角色信息
     * @param permissionIdSet 角色权限信息
     * @return 是否成功
     */
    boolean updateById(SysRoleDTO sysRoleDTO, Set<Long> permissionIdSet);

    /**
     * 删除角色
     *
     * @param roleIdSet 角色Id集合
     * @return 是否成功
     */
    boolean deleteByIdSet(Set<Long> roleIdSet);

    /**
     * 获取拥有/不拥有的权限的角色列表
     *
     * @param code          权限code
     * @param hasPermission 是否拥有权限
     * @return 角色Id列表
     */
    List<Long> listRoleIdWithPermission(String code, boolean hasPermission);

}
