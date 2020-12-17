package com.elison.platform.user.shiro;

import com.elison.platform.user.enums.LoginEnum;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @author elison
 */
public class MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        MyShiroToken tk = (MyShiroToken) authcToken;
        //如果是免密登录直接返回true
        if (LoginEnum.NO_PASSWORD.equals(tk.getType())) {
            return true;
        }
        //不是免密登录，调用父类的方法
        return super.doCredentialsMatch(tk, info);
    }
}
