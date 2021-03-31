package com.faw.oauth.domian.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: Grant
 * @Package com.faw.oauth.domian.entity
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/26/0026 3:52
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/26/0026     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class Grant implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "role";
    }
}
