package com.elison.platform.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.enums.UserStatusEnum;
import com.elison.platform.user.model.dto.SysPermissionDTO;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.model.request.ActiveSelfUserInfoReq;
import com.elison.platform.user.model.request.UpdateSelfUserInfoReq;
import com.elison.platform.user.model.response.GetSelfUserInfoResp;
import com.elison.platform.user.service.SysPermissionService;
import com.elison.platform.user.service.SysRoleService;
import com.elison.platform.user.service.SysUserService;
import com.elison.platform.user.shiro.MyShiroToken;
import com.elison.platform.user.shiro.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author elison
 */
@ResponseResultBody
@RestController
@RequestMapping("/user/me")
@Api("管理个人用户功能")
public class SelfUserManageController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @PutMapping("/active")
    @ApiOperation("激活个人账号(激活后会自动登陆)")
    public String activeSelfUserInfo(@RequestBody @Valid ActiveSelfUserInfoReq req) {
        SysUserDTO sysUserDTO = sysUserService.getOneByPhone(req.getPhone());
        // 如果用户不存在/账户被删除,抛此异常
        if (sysUserDTO == null || UserStatusEnum.DELETED.equals(sysUserDTO.getUserStatus())) {
            throw new UnknownAccountException();
        }
        // 如果用户已经激活,抛此异常
        if (UserStatusEnum.NORMAL.equals(sysUserDTO.getUserStatus())) {
            throw new ResultException(CodeEnum.ACTIVE_AGAIN_USER);
        }
        // 如果用户被禁用,抛此异常
        if (UserStatusEnum.FREEZE.equals(sysUserDTO.getUserStatus())) {
            throw new LockedAccountException();
        }
        // 校验验证码
//        CodeVerifyUtil.checkCode(req.getCode(), req.getPhone(), CodeTypeEnum.ACTIVE_CODE);
        // 更新用户状态
        SysUserDTO updateSysUserDTO = new SysUserDTO();
        updateSysUserDTO.setId(sysUserDTO.getId());
        updateSysUserDTO.setUserStatus(UserStatusEnum.NORMAL);
        if (sysUserService.updateById(updateSysUserDTO)) {
            SecurityUtils.getSubject().login(new MyShiroToken(sysUserDTO.getUserName()));
        } else {
            throw new ResultException(CodeEnum.UPDATE_ERROR);
        }
        return "激活成功";
    }

    @GetMapping("")
    @ApiOperation("查询个人信息")
    @RequiresUser
    public GetSelfUserInfoResp getSelfUserInfo() {
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        GetSelfUserInfoResp resp = sysUserDTO.convertToResponse(GetSelfUserInfoResp.class);
        resp.setUserStatus(sysUserDTO.getUserStatus().getCode());
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.listSysRolesByUserId(sysUserDTO.getId());
        List<SysPermissionDTO> sysPermissionDTOList = new ArrayList<>();
        sysRoleDTOList.forEach(sysRoleDTO ->
                sysPermissionDTOList.addAll(sysPermissionService.listSysPermissionsByRoleId(sysRoleDTO.getId())));
        resp.setPermissionList(sysPermissionDTOList.stream()
                .map(SysPermissionDTO::getPermission).collect(Collectors.toList()));
        return resp;
    }

    @PutMapping("")
    @ApiOperation("修改个人信息")
    @RequiresUser
    public String updateSelfUserInfo(@RequestBody @Valid UpdateSelfUserInfoReq req) {
        SysUserDTO sysUserDTO = UserUtil.getCurrentUser();
        if (ObjectUtil.isNotEmpty(req.getNewPassword()) && !req.getNewPassword().equals(req.getNewPasswordAgain())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "两次密码不相同");
        }
        if (ObjectUtil.isNotEmpty(req.getPhone())) {
            // 校验验证码
//            CodeVerifyUtil.checkCode(req.getPhoneCode(), req.getPhone(), CodeTypeEnum.UPDATE_PHONE_CODE);
            SysUserDTO oldUser = sysUserService.getOneByPhone(req.getPhone());
            if (oldUser != null && !oldUser.getId().equals(sysUserDTO.getId())) {
                throw new ResultException(CodeEnum.REQ_ERROR, "该手机号已被绑定，请更换手机号");
            }
        }
        // 更新用户信息
        SysUserDTO updateSysUserDTO = req.convertToDTO(SysUserDTO.class);
        updateSysUserDTO.setId(sysUserDTO.getId());
        if (!sysUserService.updateById(updateSysUserDTO)) {
            throw new ResultException(CodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }
}
