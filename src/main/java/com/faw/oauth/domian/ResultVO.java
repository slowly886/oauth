package com.faw.oauth.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.faw.oauth.config.ResponseCode;
import com.faw.oauth.constants.Constants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author tuzongxun
 * @Title: ResultVO.java
 * @Package com.faw.tsp.ownership.domain.vo
 * @Description:车辆归属服务统一响应结果
 * @Email 1160569243@qq.com
 * @version: v1.0.0
 * @date: 2019年5月27日 上午9:13:49
 * @Copyright: 2019 www.cusc.com Inc. All rights reserved. <br/>
 * 注意：本内容仅限于联通智网科技有限公司内部传阅，禁止外泄以及用于其他的商业目<br/>
 * @since JDK 1.8
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 6424892182374872984L;

    /**
     * 结果状态
     */
    private String code;

    /**
     * 描述信息
     */
    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp = new Date();

/*    *//**
     * 错误码
     *//*
    private String errorCode;

    *//**
     * 错误描述
     *//*
    private String errorMessage;*/

    /**
     * 数据结果集
     */
    private T data;

    public ResultVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
    }

    public ResultVO(T data) {
        this.code = Constants.RETURN_SUCCESS_CODE;
        this.msg = Constants.RETURN_SUCCESS_MSG;
        this.data = data;
    }
}
