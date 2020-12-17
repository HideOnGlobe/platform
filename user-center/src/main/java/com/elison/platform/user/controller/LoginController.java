package com.elison.platform.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.enums.UserStatusEnum;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.model.request.LoginByPhoneAndCodeReq;
import com.elison.platform.user.model.request.LoginByPhoneAndPasswordReq;
import com.elison.platform.user.model.request.LoginByUserNameAndPasswordReq;
import com.elison.platform.user.model.request.UpdatePasswordReq;
import com.elison.platform.user.service.SysUserService;
import com.elison.platform.user.shiro.MyShiroToken;
import com.elison.platform.user.shiro.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author elison
 */
@ResponseResultBody
@RestController
@RequestMapping("/user")
@Api("登录功能")
public class LoginController {

    private static final String SUCC_MSG = "登录成功";

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/name/password")
    @ApiOperation("用户名密码登录")
    public String loginByUserNameAndPassword(@RequestBody @Valid LoginByUserNameAndPasswordReq req) {
        SecurityUtils.getSubject().login(new MyShiroToken(req.getUserName(), req.getPassword()));
        return SUCC_MSG;
    }

    @PostMapping("/phone/password")
    @ApiOperation("手机号密码登录")
    public String loginByPhoneAndPassword(@RequestBody @Valid LoginByPhoneAndPasswordReq req) {
        SysUserDTO sysUserDTO = sysUserService.getOneByPhone(req.getPhone());
        // 如果用户不存在/账户被删除,抛此异常
        if (sysUserDTO == null || UserStatusEnum.DELETED.equals(sysUserDTO.getUserStatus())) {
            throw new UnknownAccountException();
        }
        SecurityUtils.getSubject().login(new MyShiroToken(sysUserDTO.getUserName(), req.getPassword()));
        return SUCC_MSG;
    }

    @PutMapping("/phone/password")
    @ApiOperation("根据手机号修改密码(验证码修改成功后会自动登陆)")
    public String updatePasswordByPhone(@RequestBody @Valid UpdatePasswordReq req) {
        if (!req.getNewPassword().equals(req.getNewPasswordAgain())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "两次密码不相同");
        }
        if (ObjectUtil.isEmpty(req.getPasswordCode())) {
            throw new ResultException(CodeEnum.REQ_ERROR, "验证码不能为空");
        }
        SysUserDTO userDTO = null;
        boolean login = false;
        try {
            userDTO = UserUtil.getCurrentUser();
            req.setPhone(userDTO.getPhone());
        } catch (Exception e) {
            if (ObjectUtil.isEmpty(req.getPhone())) {
                throw new ResultException(CodeEnum.REQ_ERROR, "手机号不能为空");
            }
            userDTO = sysUserService.getOneByPhone(req.getPhone());
            // 如果用户不存在/账户被删除,抛此异常
            if (userDTO == null || UserStatusEnum.DELETED.equals(userDTO.getUserStatus())) {
                throw new UnknownAccountException();
            }
            login = true;
        }
        // 校验验证码
//        CodeVerifyUtil.checkCode(req.getPasswordCode(), req.getPhone(), CodeTypeEnum.SET_PASSWORD_CODE);
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setId(userDTO.getId());
        sysUserDTO.setPassword(req.getNewPassword());
        if (sysUserService.updateById(sysUserDTO)) {
            if (login) {
                SecurityUtils.getSubject().login(new MyShiroToken(sysUserDTO.getUserName()));
            }
            return "修改成功";
        } else {
            throw new ResultException(CodeEnum.UPDATE_ERROR);
        }
    }

    @PostMapping("/phone/code")
    @ApiOperation("手机号验证码登录")
    public String loginByPhoneAndCode(@RequestBody @Valid LoginByPhoneAndCodeReq req) {
        SysUserDTO sysUserDTO = sysUserService.getOneByPhone(req.getPhone());
        // 如果用户不存在/账户被删除,抛此异常
        if (sysUserDTO == null || UserStatusEnum.DELETED.equals(sysUserDTO.getUserStatus())) {
            throw new UnknownAccountException();
        }
        // 校验验证码
//        CodeVerifyUtil.checkCode(req.getCode(), req.getPhone(), CodeTypeEnum.LOGIN_CODE);
        SecurityUtils.getSubject().login(new MyShiroToken(sysUserDTO.getUserName()));
        return SUCC_MSG;
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "已登出";
    }

}
