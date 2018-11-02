package org.code4everything.boot.xtool.bean;

import org.code4everything.boot.xtool.constant.IntegerConsts;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/10/30
 */
public class ResponseResult<T extends Serializable> implements Serializable {

    /**
     * 错误码
     */
    private int code = IntegerConsts.TWO_HUNDRED;

    /**
     * 消息
     */
    private String msg = "请求成功";

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    /**
     * 无参构造函数
     */
    public ResponseResult() {}

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public ResponseResult(T data) {
        this.data = data;
    }

    /**
     * 设置消息和数据
     *
     * @param msg 消息
     * @param data 数据
     */
    public ResponseResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    /**
     * 设置错误码和消息
     *
     * @param code 错误码
     * @param msg 消息
     */
    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     *
     * @return 当前对象
     */
    public ResponseResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 获取消息
     *
     * @return 消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     *
     * @return 当前对象
     */
    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     *
     * @return 当前对象
     */
    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
