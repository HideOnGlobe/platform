package com.elison.platform.user.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.constants
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/27 16:35
 * @UpdateDate: 2020/9/27 16:35
 **/
public class RoleConstant {

    public static final long ROOT = 1L;
    public static final long USE = 10L;
    public static final long PLATFORM_ADMIN = 20L;

    public static Set<Long> cantOperationRoleSet = new HashSet<>();
    static {
        cantOperationRoleSet.add(ROOT);
        cantOperationRoleSet.add(USE);
        cantOperationRoleSet.add(PLATFORM_ADMIN);
    }
}
