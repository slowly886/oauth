package com.faw.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: BootOAuth2Exception
 * @Package com.faw.oauth.config
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/26/0026 18:07
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/26/0026     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class BootOAuth2Exception extends OAuth2Exception {
    public BootOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public BootOAuth2Exception(String msg) {
        super(msg);
    }
}
