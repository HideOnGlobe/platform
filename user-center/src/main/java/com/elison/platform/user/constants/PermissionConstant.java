package com.elison.platform.user.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限的添加在此文件中直接添加静态字段即可
 * 默认会在root角色中添加所有权限，下属角色请通过管理系统添加权限
 * 注意map中的注释添加，如果未添加即运行程序，需要手动进行数据库操作
 * 不支持删除权限，请手动进行数据库操作，并对角色相关权限进行删除
 */
public class PermissionConstant {
    private static Map<String, String> map = new HashMap<>();

    /**管理员权限 start*/
    public static final String ADMIN = "ADMIN";
    static {
        map.put(ADMIN, "管理员权限");
    }
    /**管理员权限 start*/

    /**管理用户管理权限 start*/
    public static final String GET_ADMIN_USER = "GET_ADMIN_USER";
    public static final String INSERT_ADMIN_USER = "INSERT_ADMIN_USER";
    public static final String UPDATE_ADMIN_USER = "UPDATE_ADMIN_USER";
    public static final String DELETE_ADMIN_USER = "DELETE_ADMIN_USER";
    static {
        map.put(GET_ADMIN_USER, "查看管理用户权限");
        map.put(INSERT_ADMIN_USER, "新增管理用户权限");
        map.put(UPDATE_ADMIN_USER, "修改管理用户权限");
        map.put(DELETE_ADMIN_USER, "删除管理用户权限");
    }
    /**管理用户管理权限 end*/

    /**角色管理权限 start*/
    public static final String INSERT_ROLE = "INSERT_ROLE";
    public static final String UPDATE_ROLE = "UPDATE_ROLE";
    public static final String DELETE_ROLE = "DELETE_ROLE";
    static {
        map.put(INSERT_ROLE, "新增角色权限");
        map.put(UPDATE_ROLE, "修改角色权限");
        map.put(DELETE_ROLE, "删除角色权限");
    }
    /**角色管理权限 end*/

}
