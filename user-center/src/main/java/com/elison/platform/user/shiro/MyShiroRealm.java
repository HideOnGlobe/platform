package com.elison.platform.user.shiro;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.user.enums.UserStatusEnum;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.shiro
 * @Description: -realm实现类,用于实现具体的验证和授权方法
 * @Author: elison
 * @CreateDate: 2019/9/7 10:34
 * @UpdateDate: 2019/9/7 10:34
 **/
public class MyShiroRealm extends AuthorizingRealm {

    /**
     * 必须使用懒加载
     * 否则这些servlet将不通过BeanPostProcessors创建，将直接创建对象实例而不是代理类
     */
    @Autowired
    @Lazy
    private SysUserService sysUserService;
    @Autowired
    @Lazy
    private SysRoleService sysRoleService;
    @Autowired
    @Lazy
    private SysPermissionService sysPermissionService;

    /**
     * 用于授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 从PrincipalCollection中获得用户信息
        SysUserDTO user = (SysUserDTO) principalCollection.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        // 根据用户名来查询数据库赋予用户角色,权限（查数据库）
        if (StringUtils.isNotBlank(user.getUserName())) {
            SysUserDTO sysUserDTO = sysUserService.getOneByUserName(user.getUserName());
            Set<SysRoleDTO> sysRoleDTOSet = new HashSet<>(sysRoleService.listSysRolesByUserId(sysUserDTO.getId()));
            roles.addAll(sysRoleDTOSet.stream().map(SysRoleDTO::getName).collect(Collectors.toList()));
            Set<SysPermissionDTO> sysPermissionDTOSet = new HashSet<>();
            sysRoleDTOSet.forEach(sysRoleDTO -> sysPermissionDTOSet.addAll(sysPermissionService.listSysPermissionsByRoleId(sysRoleDTO.getId())));
            permissions.addAll(sysPermissionDTOSet.stream().map(SysPermissionDTO::getPermission).collect(Collectors.toList()));
        }

        // 更新以上代码
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //添加权限
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 方面用于加密 参数：AuthenticationToken是从表单穿过来封装好的对象
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 将AuthenticationToken强转为AuthenticationToken对象
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        // 获得从表单传过来的用户名
        String username = upToken.getUsername();

        SysUserDTO user = sysUserService.getOneByUserName(username);
        // 如果用户不存在/账户被删除,抛此异常
        if (user == null || UserStatusEnum.DELETED.equals(user.getUserStatus())) {
            throw new UnknownAccountException();
        }
        // 如果用户未被激活,抛此异常
        if (UserStatusEnum.NOT_ACTIVE.equals(user.getUserStatus())) {
            throw new ResultException(CodeEnum.LOGIN_NOT_ACTIVE_USER);
        }
        // 如果用户被禁用,抛此异常
        if (UserStatusEnum.FREEZE.equals(user.getUserStatus())) {
            throw new LockedAccountException();
        }

        Set<SysRoleDTO> sysRoleDTOSet = new HashSet<>(sysRoleService.listSysRolesByUserId(user.getId()));
        user.setRoleSet(sysRoleDTOSet);
        Set<SysPermissionDTO> sysPermissionDTOSet = new HashSet<>();
        sysRoleDTOSet.forEach(sysRoleDTO -> sysPermissionDTOSet.addAll(sysPermissionService.listSysPermissionsByRoleId(sysRoleDTO.getId())));
        user.setPermissionSet(sysPermissionDTOSet);

        // 认证的实体信息，可以是username，也可以是用户的实体类对象，这里用的用户名
        Object principal = user;
        // 从数据库中查询的密码
        Object credentials = user.getPassword();
        // 颜值加密的颜，可以用用户名
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        // 当前realm对象的名称，调用分类的getName()
        String realmName = this.getName();

        // 创建SimpleAuthenticationInfo对象，并且把username和password等信息封装到里面
        // 用户密码的比对是Shiro帮我们完成的
        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }


}
