package com.elison.platform.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.commons.mybatis.MyMapper;
import com.elison.platform.user.model.dao.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.mapper
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 16:31
 * @UpdateDate: 2020/9/8 16:31
 **/
public interface SysUserMapper extends MyMapper<SysUser> {

    IPage<SysUser> pageUserByManage(IPage<SysUser> page, @Param("sysUser") SysUser sysUser,
                                    @Param("roleIdList") List<Long> roleIdList);

    @Insert("insert into `user_wallet` " +
            "(`id`, `user_id`, `modou`, `version`) " +
            "value " +
            "(#{id}, #{userId}, 0, 0)")
    int insertWallet(Long id, Long userId);


    /**
     * 用户新增角色权限
     *
     * @param userId           用户ID
     * @param roleIdCollection 角色列表
     * @return 新增角色权限
     */
    @Insert("<script> " +
            "insert into sys_user_role(user_id, role_id) values" +
            "<foreach collection='roleIdCollection' item='item' separator=','>" +
            "(#{userId}, #{item})" +
            "</foreach> " +
            "</script>")
    int addUserRoleCollection(@Param("userId") Long userId,
                              @Param("roleIdCollection") Collection<Long> roleIdCollection);


    /**
     * 用户删除角色权限
     *
     * @param userId 用户ID
     * @return 删除角色权限
     */
    @Delete("delete from sys_user_role " +
            "where user_id = #{userId}")
    int deleteUserRole(@Param("userId") Long userId);

}
