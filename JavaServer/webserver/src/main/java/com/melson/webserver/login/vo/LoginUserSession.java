package com.melson.webserver.login.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户session
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
public class LoginUserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登录时间
     */
    private Date login;
    /**
     * sessionId
     */
    private String sessionId;

    public LoginUserSession() {

    }

    public LoginUserSession(Integer id, String loginCode, String realName, Date login, String sessionId) {
        this.id = id;
        this.loginCode = loginCode;
        this.realName = realName;
        this.login = login;
        this.sessionId = sessionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getLogin() {
        return login;
    }

    public void setLogin(Date login) {
        this.login = login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
