package com.elison.platform.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.elison.platform.user.mapper.SysPermissionMapper;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<SysPermissionDTO> listSysPermissionsByRoleId(Long roleId) {
        return CollectionUtil.emptyIfNull(sysPermissionMapper.listSysPermissionsByRoleId(roleId)).stream().map(
                sysPermission -> sysPermission.convertToDTO(SysPermissionDTO.class)).collect(Collectors.toList());
    }
}
