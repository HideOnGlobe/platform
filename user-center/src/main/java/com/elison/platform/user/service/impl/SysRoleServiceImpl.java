package com.elison.platform.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.user.enums.RoleStatusEnum;
import com.elison.platform.user.mapper.SysRoleMapper;
import com.elison.platform.user.model.dao.SysRole;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author elison
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<SysRoleDTO> listSysRolesByUserId(Long userId) {
        return CollectionUtil.emptyIfNull(sysRoleMapper.listSysRolesByUserId(userId)).stream().map(
                sysRole -> sysRole.convertToDTO(SysRoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean containsRoleName(String roleName) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", roleName);
        return sysRoleMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(SysRoleDTO sysRoleDTO, Set<Long> permissionIdSet) {
        sysRoleDTO.setRoleStatus(RoleStatusEnum.NORMAL);
        SysRole sysRole = sysRoleDTO.convertToDO(SysRole.class);
        if (sysRoleMapper.insert(sysRole) > 0) {
            if (sysRoleMapper.addSysRolePermissionCollection(sysRole.getId(), permissionIdSet) != permissionIdSet.size()) {
                throw new ResultException(CodeEnum.ADD_ERROR);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<SysRoleDTO> list() {
        return CollectionUtil.emptyIfNull(sysRoleMapper.selectList(Wrappers.emptyWrapper())).stream().map(
                sysRole -> sysRole.convertToDTO(SysRoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public SysRoleDTO getById(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        return sysRole != null ? sysRole.convertToDTO(SysRoleDTO.class) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysRoleDTO sysRoleDTO, Set<Long> permissionIdSet) {
        if (sysRoleMapper.updateById(sysRoleDTO.convertToDO(SysRole.class)) > 0) {
            if (ObjectUtil.isNotNull(permissionIdSet)) {
                sysRoleMapper.deleteSysRoleAllPermission(sysRoleDTO.getId());
                if (permissionIdSet.size() != 0 &&
                        sysRoleMapper.addSysRolePermissionCollection(sysRoleDTO.getId(), permissionIdSet)
                                != permissionIdSet.size()) {
                    throw new ResultException(CodeEnum.UPDATE_ERROR);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIdSet(Set<Long> roleIdSet) {
        int num = 0;
        for (Long id : roleIdSet) {
            if (deleteById(id)) {
                num++;
            }
        }
        if (num != roleIdSet.size()) {
            throw new ResultException(CodeEnum.DELETE_ERROR);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Long> listRoleIdWithPermission(String permission, boolean hasPermission) {
        if (hasPermission) {
            return sysRoleMapper.listRoleIdHasPermission(permission);
        } else {
            return sysRoleMapper.listRoleIdNotHasPermission(permission);
        }
    }

    @Override
    @Transactional(propagation= Propagation.MANDATORY, rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        SysRole entity = new SysRole();
        entity.setId(id);
        entity.setRoleStatus(RoleStatusEnum.DELETED);
        return sysRoleMapper.updateById(entity) > 0;
    }
}
