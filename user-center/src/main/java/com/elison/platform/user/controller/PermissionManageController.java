package com.elison.platform.user.controller;

import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.constants.PermissionConstant;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.model.request.GetUserRolePermissionListReq;
import com.elison.platform.user.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseResultBody
@RestController
@RequestMapping("/permission")
@Api("权限管理")
public class PermissionManageController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/list")
    @ApiOperation("查询角色权限列表")
    @RequiresPermissions({PermissionConstant.ADMIN})
    public Map<Long, String> getUserRoleList(@Valid GetUserRolePermissionListReq req) {
        Map<Long, String> map = new HashMap<>();
        List<SysPermissionDTO> sysPermissionDTOList = sysPermissionService.listSysPermissionsByRoleId(req.getRoleId());
        sysPermissionDTOList.forEach(sysPermissionDTO -> map.put(sysPermissionDTO.getId(), sysPermissionDTO.getName()));
        return map;
    }
}
