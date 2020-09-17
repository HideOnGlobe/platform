package com.elison.platform.user.mapper;

import com.elison.platform.commons.mybatis.MyMapper;
import com.elison.platform.user.model.dao.SysRoleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.mapper
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 16:32
 * @UpdateDate: 2020/9/8 16:32
 **/
public interface SysRoleMapper extends MyMapper<SysRoleDO> {

    /**
     * 根据用户Id获取角色列表
     * @param userId 用户ID
     * @return 用户角色列表
     */
    @Select("select sr.* " +
            "from sys_role sr left join sys_user_role sur on sr.id = sur.role_id " +
            "where sur.user_id = #{userId}")
    List<SysRoleDO> listSysRolesByUserId(@Param("userId") Long userId);
}
