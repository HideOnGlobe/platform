package com.elison.platform.user.shiro;

import com.elison.platform.user.enums.LoginEnum;
import org.apache.shiro.authc.UsernamePasswordToken;

public class MyShiroToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -2564928913725078138L;

    private LoginEnum type;


    public MyShiroToken() {
        super();
    }


    public MyShiroToken(String username, String password, LoginEnum type, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.type = type;
    }

    /**
     * 免密登录
     */
    public MyShiroToken(String username) {
        super(username, "", false, null);
        this.type = LoginEnum.NO_PASSWORD;
    }

    /**
     * 账号密码登录
     */
    public MyShiroToken(String username, String password) {
        super(username, password, false, null);
        this.type = LoginEnum.PASSWORD;
    }

    public LoginEnum getType() {
        return type;
    }


    public void setType(LoginEnum type) {
        this.type = type;
    }
}
