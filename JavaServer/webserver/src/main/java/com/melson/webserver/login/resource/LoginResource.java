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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /**
     * 修改密码
     *
     * @param pwd 新密码
     * @return
     */
    @GetMapping(value = "/updatePwd")
    public Result updatePwd(HttpServletRequest request, String pwd) {
        Integer userId = getLoginUserId(request);
        if (userId == null) {
            return failure(SysRespCode.LOGIN_TIME_OUT, "登录超时");
        }
        User user = userService.get(userId);
        if (user == null) {
            return failure(SysRespCode.USER_ID_IS_INVALID, "用户id无效");
        }
        if (StringUtils.isEmpty(pwd)) {
            return failure(SysRespCode.USER_PWD_IS_NULL, "用户密码为空");
        }
        String newPwd = MD5Util.string2MD5(pwd);
        if (newPwd.equals(user.getPassword())) {
            return failure(SysRespCode.USER_PWD_IS_REPEAT, "密码相同");
        }
        userService.updatePwd(userId, newPwd);
        return success(user);
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

    @RequestMapping(value = "/restPassword", method = RequestMethod.POST)
    public Result ResetPass(@RequestBody UserVo userVo, HttpServletRequest request) {
        if (userVo.hasEmptyParams()) return this.GenerateResult(ResultType.ParameterNeeded);
        Result result = new Result();
        // 修改逻辑
        System.out.println("Rest Call: /login/restPassword ...");
        return result;
    }

}