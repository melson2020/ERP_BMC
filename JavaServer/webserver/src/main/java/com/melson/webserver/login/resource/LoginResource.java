package com.melson.webserver.login.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.constants.SysConstants;
import com.melson.base.constants.SysRespCode;
import com.melson.base.entity.User;
import com.melson.base.service.IUser;
import com.melson.base.utils.CookieUtil;
import com.melson.base.utils.MD5Util;
import com.melson.webserver.login.vo.LoginUserSession;
import com.melson.webserver.login.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录controller
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@RestController
@RequestMapping(value = "/login")
public class LoginResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private IUser userService;

    /**
     * 获取用户登录session
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/getLoginSession")
    public Result getLoginSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        return success(httpSession.getAttribute(SysConstants.LOGIN_SESSION_USER_SESSION));
    }

    /**
     * 用户登录
     *
     * @param request
     * @param loginInfo
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody User loginInfo) {
        User user = userService.SystemLogin(loginInfo);
        if (user == null) {
            return failure(SysRespCode.LOGIN_FAIL, "登录失败");
        }
        Date date=new Date();
        Date date2 = user.getCompany().getExpireDate();
        long today=date.getTime();
        long defineDate=date2.getTime();
        int days = (int) Math.floor((defineDate-today)/(24*3600*1000));

        if (today > defineDate) {
            return failure(SysRespCode.LOGIN_FAIL, "商户已过期，请联系软件服务商 melson2020@163.com续费！");
        }
        else
        {
            if(days<30)
            {
                user.setMsg("商户还有"+days+"天过期，请尽快联系软件服务商 melson2020@163.com！");
            }
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute(SysConstants.LOGIN_SESSION_USER_ID, user.getId());
            LoginUserSession userSession = new LoginUserSession(user.getId(), user.getLoginName(), user.getUserName(), new Date(), httpSession.getId());
            httpSession.setAttribute(SysConstants.LOGIN_SESSION_USER_SESSION, userSession);
            Map<String, String> cookieMap = new HashMap<>();
            cookieMap.put(SysConstants.SYS_COOKIE_LOGIN_CODE, user.getLoginName());
            cookieMap.put(SysConstants.SYS_COOKIE_USER_ID, user.getUserId());
            CookieUtil.saveCookies(response, cookieMap);
            logger.info("用户[{}]登录成功", user.getId() + "-" + user.getLoginName() + "-" + user.getUserName());
            return success(user);
        }
    }

    /**
     * 注销登录
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            return success();
        }
        httpSession.removeAttribute(SysConstants.LOGIN_SESSION_USER_ID);
        httpSession.removeAttribute(SysConstants.LOGIN_SESSION_USER_SESSION);
        httpSession.invalidate();
        logger.info("用户注销登录成功");
        return success();
    }

    /**
     * 修改登录密码
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/restPassword")
    public Result restPassword(@RequestBody UserVo vo) {
        if (vo.hasEmptyParams()) {
            return this.GenerateResult(ResultType.ParameterNeeded);
        }
        User user = userService.findByUserId(vo.getUserId());
        if (user == null) {
            return failure(SysRespCode.USER_ID_IS_INVALID, "用户id无效");
        }
        if (StringUtils.isEmpty(vo.getNewPass())) {
            return failure(SysRespCode.USER_PWD_IS_NULL, "用户密码为空");
        }
        String newPwd = MD5Util.string2MD5(vo.getNewPass());
        if (newPwd.equals(user.getPassword())) {
            return failure(SysRespCode.USER_PWD_IS_REPEAT, "密码相同");
        }
        userService.updatePwd(user.getId(), newPwd);
        return success(user);
    }
}