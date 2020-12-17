package com.elison.platform.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.constants.PermissionConstant;
import com.elison.platform.user.constants.RoleConstant;
import com.elison.platform.user.enums.RoleStatusEnum;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.model.request.AddRoleReq;
import com.elison.platform.user.model.request.DeleteRoleReq;
import com.elison.platform.user.model.request.UpdateRoleReq;
import com.elison.platform.user.service.SysPermissionService;
import com.elison.platform.user.service.SysRoleService;
import com.elison.platform.user.shiro.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@ResponseResultBody
@RestController
@RequestMapping("/role")
@Api("角色管理")
public class RoleManageController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/list")
    @ApiOperation("查询可管理角色列表")
    @RequiresPermissions({PermissionConstant.ADMIN})
    public Map<Long, String> getUserRoleList() {
        Map<Long, String> map = new HashMap<>();
        // 获取数据库所有角色
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.list();
        // 获取当前用户所有角色
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        Set<Long> parentIdSet = sysRoleService.listSysRolesByUserId(sysUserDTO.getId()).stream()
                .map(SysRoleDTO::getId).collect(Collectors.toSet());
        // 获取当前用户所拥有角色的所有下属角色
        sysRoleDTOList.stream()
                .filter(sysRoleDTO -> !RoleStatusEnum.DELETED.equals(sysRoleDTO.getRoleStatus())
                        && parentIdSet.contains(sysRoleDTO.getParentId()))
                .forEach(sysRoleDTO -> map.put(sysRoleDTO.getId(), sysRoleDTO.getName()));
        return map;
    }

    @PostMapping("")
    @ApiOperation("新增角色")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.INSERT_ROLE})
    public String insertRole(@RequestBody @Valid AddRoleReq req) {
        // 获取当前用户所有角色
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        // 判断当前角色名称是否已存在
        if (sysRoleService.containsRoleName(req.getRoleName())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "角色名称已存在");
        }
        // 判断新建角色是否在当前用户角色之下
        Set<Long> parentIdSet = sysRoleService.listSysRolesByUserId(sysUserDTO.getId()).stream()
                .map(SysRoleDTO::getId).collect(Collectors.toSet());
        if (!parentIdSet.contains(req.getParentId())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "新建角色必须为当前角色的直属下属");
        }
        // 判断新建角色权限是否在父角色权限之内
        List<SysPermissionDTO> sysPermissionDTOList = sysPermissionService.listSysPermissionsByRoleId(req.getParentId());
        Set<Long> rolePermissionSet = sysPermissionDTOList.stream()
                .map(SysPermissionDTO::getId).collect(Collectors.toSet());
        if (!rolePermissionSet.containsAll(req.getPermissionSet())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "新建角色权限必须在当前角色权限之内");
        }
        // 新增角色及权限
        SysRoleDTO sysRoleDTO = req.convertToDTO(SysRoleDTO.class);
        sysRoleDTO.setName(req.getRoleName());
        if (!sysRoleService.insert(sysRoleDTO, req.getPermissionSet())) {
            throw new ResultException(CodeEnum.ADD_ERROR);
        }
        return "新增成功";
    }

    @PutMapping("")
    @ApiOperation("修改角色")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.UPDATE_ROLE})
    public String updateRole(@RequestBody @Valid UpdateRoleReq req) {
        checkOperationRole(Collections.singletonList(req.getRoleId()));
        // 获取当前用户所有角色
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        SysRoleDTO sysRoleDTO = sysRoleService.getById(req.getRoleId());
        // 判断当前角色名称是否已存在
        if (ObjectUtil.isNotEmpty(req.getRoleName())
                && !sysRoleDTO.getName().equals(req.getRoleName())
                && sysRoleService.containsRoleName(req.getRoleName())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "角色名称已存在");
        }
        if (ObjectUtil.isNotEmpty(req.getPermissionSet())) {
            // 判断新建角色权限是否在父角色权限之内
            List<SysPermissionDTO> sysPermissionDTOList = sysPermissionService.listSysPermissionsByRoleId(
                    sysRoleDTO.getParentId() != null ? sysRoleDTO.getParentId() : sysRoleDTO.getParentId());
            Set<Long> rolePermissionSet = sysPermissionDTOList.stream()
                    .map(SysPermissionDTO::getId).collect(Collectors.toSet());
            if (!rolePermissionSet.containsAll(req.getPermissionSet())) {
                throw new ResultException(CodeEnum.REQ_ERROR, "新建角色权限必须在当前角色权限之内");
            }
        }
        // 更新角色及权限
        sysRoleDTO.setName(req.getRoleName());
        if (!sysRoleService.updateById(sysRoleDTO, req.getPermissionSet())) {
            throw new ResultException(CodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }

    @DeleteMapping("")
    @ApiOperation("删除角色")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.DELETE_ROLE})
    public String deleteRole(@RequestBody @Valid DeleteRoleReq req) {
        checkOperationRole(req.getRoleIdSet());
        // 获取当前用户所有角色
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        Set<Long> userRoleIdSet = sysUserDTO.getRoleSet().stream().map(SysRoleDTO::getId).collect(Collectors.toSet());
        Set<Long> parentRoleIdSet = new HashSet<>();
        for (Long roleId : req.getRoleIdSet()) {
            SysRoleDTO role = sysRoleService.getById(roleId);
            parentRoleIdSet.add(role != null ? role.getParentId() : roleId);
        }
        if (!userRoleIdSet.containsAll(parentRoleIdSet)) {
            throw new ResultException(CodeEnum.REQ_ERROR, "删除角色必须为当前角色的直属下属");
        }
        if (!sysRoleService.deleteByIdSet(req.getRoleIdSet())) {
            throw new ResultException(CodeEnum.DELETE_ERROR);
        }
        return "删除成功";
    }

    private void checkOperationRole(Collection<Long> roleIdCollection) {
        for (Long roleId : roleIdCollection) {
            if (RoleConstant.cantOperationRoleSet.contains(roleId)) {
                throw new ResultException(CodeEnum.BUSSINESS_ERROR, "初始角色不允许通过配置界面修改，请联系开发人员");
            }
        }
    }
}
