package com.faw.oauth.domian;

import com.faw.oauth.utils.JwtUtils;
import lombok.Data;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: Jwt
 * @Package com.faw.oauth.domian
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/27/0027 2:12
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/27/0027     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Data
public class Jwt {
    /* 头部 */
    private String header;
    /* 负载 */
    private String payload;
    /* 签名 */
    private String signature;

    public Jwt(String payload) throws Exception {
        this.header = JwtUtils.encode(JwtUtils.DEFAULT_HEADER);
        this.payload = JwtUtils.encode(payload);
        this.signature = JwtUtils.getSignature(payload);
    }

    /* jwt最终结果 */
    @Override
    public String toString() {
        return header + "." + payload + "." + signature;
    }
}
