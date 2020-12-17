package com.elison.platform.user.mapper;

import com.elison.platform.commons.mybatis.MyMapper;
import com.elison.platform.user.model.dao.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.mapper
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 16:33
 * @UpdateDate: 2020/9/8 16:33
 **/
public interface SysPermissionMapper extends MyMapper<SysPermission> {

    /**
     * 根据角色Id获取权限列表
     * @param roleId 角色ID
     * @return 角色权限列表
     */
    @Select("select sp.* " +
            "from sys_permission sp left join sys_role_permission srp on sp.id = srp.permission_id " +
            "where srp.role_id = #{roleId}")
    List<SysPermission> listSysPermissionsByRoleId(@Param("roleId") Long roleId);
}
