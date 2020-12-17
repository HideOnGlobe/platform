package com.elison.platform.user.check;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elison.platform.user.constants.PermissionConstant;
import com.elison.platform.user.constants.RoleConstant;
import com.elison.platform.user.mapper.SysPermissionMapper;
import com.elison.platform.user.mapper.SysRoleMapper;
import com.elison.platform.user.model.dao.SysPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.check
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/28 16:04
 * @UpdateDate: 2020/9/28 16:04
 **/
@Slf4j
@Order(1)
@Component
public class PermissionCheck implements ApplicationRunner {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    private static final Class CHECK_CLAZZ = PermissionConstant.class;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("权限数据库修改检查开始");
        // 检查权限表，完善新增权限
        List<SysPermission> permissionList = sysPermissionMapper.selectList(new QueryWrapper<>());
        Set<String> permissionSet = ObjectUtil.isEmpty(permissionList) ? new HashSet<>() :
                permissionList.stream().map(SysPermission::getPermission).collect(Collectors.toSet());
        Set<String> addPermissionSet = new HashSet<>();
        Map<String, String> permissionMap = new HashMap<>();
        Field[] fields = CHECK_CLAZZ.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod)) {
                Object o = field.get(CHECK_CLAZZ);
                if (field.getType().equals(String.class)) {
                    if (!permissionSet.contains(o.toString())) {
                        addPermissionSet.add(o.toString());
                    }
                } else if (field.getType().equals(Map.class)) {
                    permissionMap = (Map<String, String>) o;
                } else {
                    log.error("权限静态表中存在异常字段");
                }
            }
        }
        if (addPermissionSet.size() > 0) {
            List<SysPermission> sysPermissionList = new ArrayList<>();
            for (String permission : addPermissionSet) {
                SysPermission sysPermission = new SysPermission();
                sysPermission.setPermission(permission);
                sysPermission.setName(permissionMap.get(permission));
                sysPermissionList.add(sysPermission);
            }
            sysPermissionMapper.insertBatchSomeColumn(sysPermissionList);
        }
        // 检查root用户权限，完善root用户权限
        permissionList = sysPermissionMapper.selectList(new QueryWrapper<>());
        Set<Long> permissionIdSet = ObjectUtil.isEmpty(permissionList) ? new HashSet<>() :
                permissionList.stream().map(SysPermission::getId).collect(Collectors.toSet());
        List<SysPermission> rootPermissionList = sysPermissionMapper.listSysPermissionsByRoleId(RoleConstant.ROOT);
        for (SysPermission sysPermission : rootPermissionList) {
            permissionIdSet.remove(sysPermission.getId());
        }
        if (ObjectUtil.isNotEmpty(permissionIdSet)) {
            sysRoleMapper.addSysRolePermissionCollection(RoleConstant.ROOT, permissionIdSet);
        }
        log.info("权限数据库修改检查结束");
    }
}
