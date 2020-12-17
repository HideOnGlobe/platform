package com.elison.platform.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dao.SysUser;
import com.elison.platform.user.model.dto.SysUserDTO;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 23:46
 * @UpdateDate: 2020/9/8 23:46
 **/
public interface SysUserService extends BaseDataService<SysUserDTO, SysUser> {

    /**
     * 根据登录名获取对应的信息
     *
     * @param userName 登录名
     * @return 用户信息
     */
    SysUserDTO getOneByUserName(String userName);

    /**
     * 根据手机号获取对应的信息
     *
     * @param phone 登录名
     * @return 用户信息
     */
    SysUserDTO getOneByPhone(String phone);

    /**
     * 禁用用户
     *
     * @param userIdList 用户列表
     * @return 是否禁用成功
     */
    boolean freezeUserList(List<Long> userIdList);

    /**
     * 逻辑删除用户集合
     *
     * @param idSet 用户ID集合
     * @return 是否成功
     */
    boolean deleteIdSet(Set<Long> idSet);

    /**
     * 查询用户列表
     *
     * @param page               分页信息
     * @param entity             查询条件
     * @param hasAdminPermission 是否拥有管理员权限
     * @return 用户分页数据
     */
    IPage<SysUserDTO> page(IPage<SysUserDTO> page, SysUserDTO entity, boolean hasAdminPermission);

}
