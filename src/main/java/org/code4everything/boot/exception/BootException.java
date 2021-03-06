package org.code4everything.boot.exception;

import org.code4everything.boot.bean.ExceptionBean;
import org.code4everything.boot.constant.MessageConsts;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常
 *
 * @author pantao
 * @since 2018/11/30
 **/
public class BootException extends RuntimeException {

    /**
     * 错误码
     *
     * @since 1.0.4
     */
    private Integer code = cn.hutool.http.HttpStatus.HTTP_INTERNAL_ERROR;

    /**
     * 消息
     *
     * @since 1.0.4
     */
    private String msg = MessageConsts.UNKNOWN_ERROR_ZH;

    /**
     * HTTP状态
     *
     * @since 1.0.4
     */
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * 无参构造函数
     *
     * @since 1.0.4
     */
    public BootException() {}

    /**
     * 设置消息
     *
     * @param msg 消息
     *
     * @since 1.0.4
     */
    public BootException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     * 设置消息和异常
     *
     * @param msg 消息
     * @param throwable 异常
     *
     * @since 1.0.4
     */
    public BootException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.msg = msg;
    }

    /**
     * 设置错误码和消息
     *
     * @param code 错误码
     * @param msg 消息
     *
     * @since 1.0.4
     */
    public BootException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 设置错误码，消息和异常
     *
     * @param code 错误码
     * @param msg 消息
     * @param throwable 异常
     *
     * @since 1.0.4
     */
    public BootException(int code, String msg, Throwable throwable) {
        super(msg, throwable);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 设置错误码和HTTP状态
     *
     * @param code 错误码
     * @param status HTTP状态
     *
     * @since 1.0.4
     */
    public BootException(int code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    /**
     * 设置错误码，HTTP状态和异常
     *
     * @param code 错误码
     * @param status HTTP状态
     * @param throwable 异常
     *
     * @since 1.0.4
     */
    public BootException(int code, HttpStatus status, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.status = status;
    }

    /**
     * 设置错误码，HTTP状态和消息
     *
     * @param code 错误码
     * @param status HTTP状态
     * @param msg 消息
     *
     * @since 1.0.4
     */
    public BootException(int code, HttpStatus status, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.status = status;
    }

    /**
     * 设置错误码，HTTP状态，消息和异常
     *
     * @param code 错误码
     * @param status HTTP状态
     * @param msg 消息
     * @param throwable 异常
     *
     * @since 1.0.4
     */
    public BootException(int code, HttpStatus status, String msg, Throwable throwable) {
        super(msg, throwable);
        this.msg = msg;
        this.code = code;
        this.status = status;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     *
     * @since 1.0.4
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 错误码
     *
     * @param code 错误码
     *
     * @return {@link BootException}
     *
     * @since 1.0.4
     */
    public BootException setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * 获取消息
     *
     * @return 消息
     *
     * @since 1.0.4
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     *
     * @return {@link BootException}
     *
     * @since 1.0.4
     */
    public BootException setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 获取HTTP状态
     *
     * @return HTTP状态
     *
     * @since 1.0.4
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * 设置HTTP状态
     *
     * @param status HTTP状态
     *
     * @return {@link BootException}
     *
     * @since 1.0.4
     */
    public BootException setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    /**
     * 转换成 {@link ExceptionBean}
     *
     * @return {@link ExceptionBean}
     *
     * @since 1.0.4
     */
    public ExceptionBean asExceptionBean() {
        return new ExceptionBean().setCode(code).setMsg(msg).setStatus(status);
    }
}
