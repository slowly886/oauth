package com.faw.oauth.config;

import lombok.Getter;

/**
 * 接口响应码
 *
 * @author zhoucc
 * @create 2018-07-31 16:52
 **/
public enum ResponseCode {

    /**
     * 如果一个业务中包含多个步骤，可在每个分步骤完成后返回此状态码
     */
    CONTINUE_100("100", "continue!"),

    /**
     * 请求处理成功
     */
    SUCCESS_200("0", "success"),


    /**
     * 没有权限
     */
    UNAUTHORIZED("10002", "Unauthorized"),

    /**
     * token失效
     */
    TOKEN_ERROR("10003", "Token error"),

    /**
     * 请求的资源未找到
     */
    NOT_FOUND_404("404", "resource not found!"),

    /**
     * 方法不被允许
     */
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),

    /**
     * 请求资源不可接受，一般用于hibernate-validation返回异常时返回此响应码
     */
    NOT_ACCEPTABLE_406("406", "resource not acceptable!"),

    NOT_ACCEPTABLE_400002("400002", "获取授权码错误"),

    /**
     * 服务器内部异常
     */
    INTERNAL_SERVER_ERROR_10001("10001", "internal server error!"),

    /**
     * 网关超时
     */
    GATEWAY_TIMEOUT_504("504", "gateway timeout!"),

    /**
     * 如果一个业务中包含多个步骤，可在每个分步骤完成后返回此状态码
     */
    BUSINESSEXCEPTION_1001("1001", "BusinessException");

    @Getter
    private String code;
    @Getter
    private String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
