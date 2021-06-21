package com.melson.base;

import com.melson.base.constants.SysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(BaseResource.class);

    @ExceptionHandler()
    @ResponseStatus(reason = "系统错误", value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception e) {
        logger.error("系统错误", e);
        return e.getMessage();
    }

    public Result GenerateResult(ResultType type, String message) {
        Result result = new Result();
        switch (type) {
            case AccessNeeded:
                result.setResultStatus(-1);
                result.setMessage("AccessNeeded");
                break;
            case AccessDenied:
                result.setResultStatus(-1);
                result.setMessage("AccessDenied");
                break;
            case ExceptionCatched:
                result.setResultStatus(-1);
                result.setMessage(message);
                break;
            case ParameterNeeded:
                result.setResultStatus(-1);
                result.setMessage("Parameter Needed");
                break;
            default:
                break;
        }
        return result;
    }

    public Result GenerateResult(ResultType type) {
        Result result = new Result();
        switch (type) {
            case AccessNeeded:
                result.setResultStatus(-1);
                result.setMessage("AccessNeeded");
                break;
            case AccessDenied:
                result.setResultStatus(-1);
                result.setMessage("AccessDenied");
                break;
            case ExceptionCatched:
                result.setResultStatus(-1);
                result.setMessage("Exception Catched");
                break;
            case ParameterNeeded:
                result.setResultStatus(-1);
                result.setMessage("Parameter Needed");
                break;
            default:
                break;
        }
        return result;
    }

    protected Result success() {
        return new Result();
    }

    protected Result success(Object object) {
        return new Result(object);
    }

    protected Result failure(Integer respCode, String msg) {
        return new Result(respCode, msg);
    }

    protected Result warning(String msg) {
        Result result=new Result();
        result.setMessage(msg);
        return result;
    }

    protected Integer getLoginUserId(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            return null;
        }
        return (Integer) httpSession.getAttribute(SysConstants.LOGIN_SESSION_USER_ID);
    }
}
