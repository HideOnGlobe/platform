package com.elison.platform.bussiness.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.center.controller
 * @Description: -
 * @Author: elison
 * @CreateDate: 2019/9/7 19:55
 * @UpdateDate: 2019/9/7 19:55
 **/
@Api("111")
@RestController
public class ShiroTest {
    @PostMapping("login")
    public void login(@RequestBody String name, @RequestBody String password) {
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            token.setRememberMe(true);
            try {
                user.login(token);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
