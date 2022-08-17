package com.webserver.http;

/**
 * @author Freg
 * @time 2022/8/16  16:19
 *
 * 空请求异常
 * 当HttpServletRequest在解析请求时发现本次为空请求就会抛出此异常
 */
public class EmptyRequestException extends Exception{
    public EmptyRequestException() {
    }

    public EmptyRequestException(String message) {
        super(message);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }

    public EmptyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
