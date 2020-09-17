package com.elison.platform.bussiness.service;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.center.Service
 * @Description: -
 * @Author: elison
 * @CreateDate: 2019/9/7 20:08
 * @UpdateDate: 2019/9/7 20:08
 **/
@Component
public class ShiroServiceTest {

    @RequiresPermissions(value = {"admin:query","user:query"},logical = Logical.OR)
    public void test() {
        System.out.println("test-succcess");
    }
}
