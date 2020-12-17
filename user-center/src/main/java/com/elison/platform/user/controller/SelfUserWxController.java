package com.elison.platform.user.controller;

import com.elison.platform.commons.result.ResponseResultBody;
import com.elison.platform.user.model.request.GetUserWxOpenIdReq;
import com.elison.platform.user.model.response.GetUserWxOpenIdResp;
import com.elison.platform.user.service.SysUserWxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.controller
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/12/1 15:49
 * @UpdateDate: 2020/12/1 15:49
 **/
@ResponseResultBody
@RestController
@RequestMapping("/user/wx")
@Api("个人微信信息功能")
public class SelfUserWxController {

    @Autowired
    private SysUserWxService sysUserWxService;

    @GetMapping("/openid")
    @ApiOperation("获取微信用户openid")
    @RequiresUser
    public GetUserWxOpenIdResp getOpenId(@RequestBody @Valid GetUserWxOpenIdReq req) {
        GetUserWxOpenIdResp resp = new GetUserWxOpenIdResp();
        resp.setOpenId(sysUserWxService.getOpenIdByCode(req.getCode()));
        return resp;
    }
}
