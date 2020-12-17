package com.elison.platform.user.mapper;

import com.elison.platform.commons.mybatis.MyMapper;
import com.elison.platform.user.model.dao.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.mapper
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 16:32
 * @UpdateDate: 2020/9/8 16:32
 **/
public interface SysRoleMapper extends MyMapper<SysRole> {

    /**
     * 根据用户Id获取角色列表
     *
     * @param userId 用户ID
     * @return 用户角色列表
     */
    @Select("select sr.* " +
            "from sys_role sr left join sys_user_role sur on sr.id = sur.role_id " +
            "where sur.user_id = #{userId} " +
            "and sr.role_status != 3")
    List<SysRole> listSysRolesByUserId(@Param("userId") Long userId);

    /**
     * 角色新增权限
     * @param roleId 角色ID
     * @param permissionIdCollection 权限列表
     * @return 新增数量
     */
    @Insert("<script> " +
            "insert into sys_role_permission(role_id, permission_id) values" +
            "<foreach collection='permissionIdCollection' item='item' separator=','>" +
            "(#{roleId}, #{item})" +
            "</foreach> " +
            "</script>")
    int addSysRolePermissionCollection(@Param("roleId") Long roleId,
                                       @Param("permissionIdCollection") Collection<Long> permissionIdCollection);

    /**
     * 删除角色所有权限
     * @param roleId 角色ID
     * @return 删除数量
     */
    @Delete("delete from sys_user_role where role_id = #{roleId}")
    int deleteSysRoleAllPermission(@Param("roleId") Long roleId);

    /**
     * 获取拥有某个权限的角色列表
     * @param permission 权限
     * @return 角色列表
     */
    @Select("select sur.role_id " +
            "from sys_role_permission sur left join sys_permission sp on sur.permission_id = sp.id " +
            "where sp.permission = #{code}")
    List<Long> listRoleIdHasPermission(String permission);

    /**
     * 获取不拥有某个权限的角色列表
     * @param permission 权限
     * @return 角色列表
     */
    @Select("select id " +
            "from sys_role " +
            "where id not in (select distinct(sur.role_id) " +
            "from sys_role_permission sur left join sys_permission sp on sur.permission_id = sp.id " +
            "where sp.permission = #{permission})")
    List<Long> listRoleIdNotHasPermission(String permission);
}
