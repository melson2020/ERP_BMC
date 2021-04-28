package com.melson.base.utils;

import com.melson.base.constants.SysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * cookie工具类
 *
 * @author wuhuan
 * @date 2021年04月28日
 */
public final class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static void saveCookies(HttpServletResponse response, Map<String, String> cookieKvMap) {
        if (cookieKvMap == null || cookieKvMap.isEmpty()) {
            logger.error("cookie键值对为空");
            return;
        }
        cookieKvMap.forEach((k, v) -> {
            Cookie cookie = new Cookie(k, v);
            cookie.setDomain(SysConstants.SYS_COOKIE_DOMAIN);
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            saveCookie(response, cookie);
        });
    }

    public static void saveCookie(HttpServletResponse response, Cookie cookie) {
        if (cookie == null) {
            logger.error("cookie为空");
            return;
        }
        response.addCookie(cookie);
    }
}
