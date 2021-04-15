package com.melson.base.constants;

/**
 * 系统通用返回码
 * 后续可以根据返回码做对应文字国际化
 *
 * @author wuhuan
 * @date 2021年04月14日
 */
public class SysRespCode {

    // 1-100系统相关
    /**
     * 成功
     */
    public static final int SUCCESS = 1;

    // 101-150 登录相关
    /**
     * 登录超时
     */
    public static final int LOGIN_TIME_OUT = 101;
    /**
     * 登录失败
     */
    public static final int LOGIN_FAIL = 102;

    // 151-200 用户管理相关
    /**
     * 用户ID无效
     */
    public static final int USER_ID_IS_INVALID = 151;
    /**
     * 用户密码为空
     */
    public static final int USER_PWD_IS_NULL = 152;
    /**
     * 密码相同
     */
    public static final int USER_PWD_IS_REPEAT = 153;
}
