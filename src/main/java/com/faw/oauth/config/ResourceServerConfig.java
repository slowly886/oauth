package com.faw.oauth.config;

import com.faw.oauth.config.handler.AuthExceptionEntryPointHandler;
import com.faw.oauth.config.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: ResourceServerConfig
 * @Package com.faw.oauth.config
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/26/0026 16:37
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/26/0026     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {

        //这里把自定义异常加进去
        resource.authenticationEntryPoint(new AuthExceptionEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
