package com.elison.platform.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.page.PageResult;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.constants.PermissionConstant;
import com.elison.platform.user.enums.UserStatusEnum;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.model.request.DeleteAdminUserReq;
import com.elison.platform.user.model.request.GetAdminUserPageReq;
import com.elison.platform.user.model.request.RegisterAdminUserReq;
import com.elison.platform.user.model.request.UpdateAdminUserReq;
import com.elison.platform.user.model.response.GetAdminUserResp;
import com.elison.platform.user.service.SysRoleService;
import com.elison.platform.user.service.SysUserService;
import com.elison.platform.user.shiro.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author elison
 */
@ResponseResultBody
@RestController
@RequestMapping("/user/admin")
@Api("管理管理员用户功能")
public class AdminUserManageController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("")
    @ApiOperation("管理用户注册")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.INSERT_ADMIN_USER})
    public String registerAdminUser(@RequestBody @Valid RegisterAdminUserReq req) {
        SysUserDTO sysUserDTO = sysUserService.getOneByUserName(req.getUserName());
        if (sysUserDTO != null) {
            throw new ResultException(CodeEnum.REQ_ERROR, "该用户名已被注册，请更换用户名");
        }
        sysUserDTO = sysUserService.getOneByPhone(req.getPhone());
        if (sysUserDTO != null) {
            throw new ResultException(CodeEnum.REQ_ERROR, "该手机号已被绑定，请更换手机号");
        }
        // 角色校验（只能添加子角色）
        if (ObjectUtil.isNotEmpty(req.getRoleList())) {
            SysUserDTO currentUser = UserUtil.getCurrentUser();
            Set<Long> roleIdSet = currentUser.getRoleSet().stream().map(SysRoleDTO::getId).collect(Collectors.toSet());
            Set<Long> parentIdSet = new HashSet<>();
            for (Long roleId : req.getRoleList()) {
                SysRoleDTO sysRoleDTO = sysRoleService.getById(roleId);
                parentIdSet.add(sysRoleDTO != null ? sysRoleDTO.getParentId() : roleId);
            }
            if (!roleIdSet.containsAll(parentIdSet)) {
                throw new ResultException(CodeEnum.REQ_ERROR, "只能为新用户赋予您的子角色");
            }
        }
        // 新建用户
        sysUserDTO = req.convertToDTO(SysUserDTO.class);
        // 添加角色
        Set<SysRoleDTO> roleSet = new HashSet<>();
        req.getRoleList().forEach(id -> {
            SysRoleDTO role = new SysRoleDTO();
            role.setId(id);
            roleSet.add(role);
        });
        sysUserDTO.setRoleSet(roleSet);
        if (!sysUserService.insert(sysUserDTO)) {
            throw new ResultException(CodeEnum.ADD_ERROR);
        }
        return "注册成功";
    }

    @GetMapping("")
    @ApiOperation("查看管理用户")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.GET_ADMIN_USER})
    public PageResult<GetAdminUserResp> getAdminUserPage(@Valid GetAdminUserPageReq req) {
        SysUserDTO sysUserDTO = req.convertToDTO(SysUserDTO.class);
        sysUserDTO.setUserStatus(UserStatusEnum.getByCode(req.getUserStatus()));
        IPage<SysUserDTO> iPage = sysUserService.page(req.convertPage(), sysUserDTO, true);
        PageResult<GetAdminUserResp> respPageResult = new PageResult<>(iPage);
        iPage.getRecords().forEach(user -> {
            GetAdminUserResp resp = user.convertToResponse(GetAdminUserResp.class);
            resp.setUserStatus(user.getUserStatus().getCode());
            resp.setRoleMap(user.getRoleSet().stream().collect(Collectors.toMap(SysRoleDTO::getId,
                    SysRoleDTO::getName)));
            respPageResult.getRecords().add(resp);
        });
        return respPageResult;
    }

    @PutMapping("")
    @ApiOperation("修改管理用户")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.UPDATE_ADMIN_USER})
    public String updateAdminUser(@RequestBody @Valid UpdateAdminUserReq req) {
        if (ObjectUtil.isNotEmpty(req.getNewPassword()) && !req.getNewPassword().equals(req.getNewPasswordAgain())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "两次密码不相同");
        }
        if (ObjectUtil.isNotEmpty(req.getPhone())) {
            SysUserDTO oldUser = sysUserService.getOneByPhone(req.getPhone());
            if (oldUser != null && !oldUser.getId().equals(req.getUserId())) {
                throw new ResultException(CodeEnum.REQ_ERROR, "该手机号已被绑定，请更换手机号");
            }
        }
        // 角色校验（只能添加子角色）
        if (ObjectUtil.isNotEmpty(req.getRoleList())) {
            SysUserDTO currentUser = UserUtil.getCurrentUser();
            Set<Long> roleIdSet = currentUser.getRoleSet().stream().map(SysRoleDTO::getId).collect(Collectors.toSet());
            Set<Long> parentIdSet = new HashSet<>();
            for (Long roleId : req.getRoleList()) {
                SysRoleDTO sysRoleDTO = sysRoleService.getById(roleId);
                parentIdSet.add(sysRoleDTO != null ? sysRoleDTO.getParentId() : roleId);
            }
            if (!roleIdSet.containsAll(parentIdSet)) {
                throw new ResultException(CodeEnum.REQ_ERROR, "只能为新用户赋予您的子角色");
            }
        }
        // 更新用户
        SysUserDTO sysUserDTO = req.convertToDTO(SysUserDTO.class);
        sysUserDTO.setId(req.getUserId());
        sysUserDTO.setPassword(req.getNewPassword());
        // 添加角色
        Set<SysRoleDTO> roleSet = new HashSet<>();
        req.getRoleList().forEach(id -> {
            SysRoleDTO role = new SysRoleDTO();
            role.setId(id);
            roleSet.add(role);
        });
        sysUserDTO.setRoleSet(roleSet);
        sysUserDTO.setUserStatus(UserStatusEnum.getByCode(req.getUserStatus()));
        if (!sysUserService.updateById(sysUserDTO)) {
            throw new ResultException(CodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }

    @DeleteMapping("")
    @ApiOperation("删除管理用户")
    @RequiresPermissions({PermissionConstant.ADMIN, PermissionConstant.DELETE_ADMIN_USER})
    public String deleteAdminUser(@RequestBody @Valid DeleteAdminUserReq req) {
        if (!sysUserService.deleteIdSet(req.getUserIdSet())) {
            throw new ResultException(CodeEnum.DELETE_ERROR);
        }
        return "删除成功";
    }
}
