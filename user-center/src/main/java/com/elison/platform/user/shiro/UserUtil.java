package com.elison.platform.user.shiro;

import com.elison.platform.commons.model.BaseDTO;
import com.elison.platform.commons.utils.BaseUserUtil;
import com.elison.platform.user.constants.RoleConstant;
import com.elison.platform.user.model.dto.SysUserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author elison
 */
public class UserUtil extends BaseUserUtil {

    public static SysUserDTO getCurrentUser() {
        return (SysUserDTO) SecurityUtils.getSubject().getPrincipal();
    }

    public static boolean isPlatformUser() {
        Set<Long> roleIdSet = getCurrentUser().getRoleSet().stream().map(BaseDTO::getId).collect(Collectors.toSet());
        return roleIdSet.contains(RoleConstant.ROOT) || roleIdSet.contains(RoleConstant.PLATFORM_ADMIN);
    }

    public static boolean hasPermission(String permission, boolean throwEx) {
        if (!SecurityUtils.getSubject().isPermitted(permission)) {
            if (throwEx) {
                throw new UnauthorizedException();
            }
            return false;
        }
        return true;
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
