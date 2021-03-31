package com.faw.oauth.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: AuthExceptionEntryPointHandler
 * @Package com.faw.oauth.config.handler
 * @Description:内部错误处理操作
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/26/0026 16:35
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/26/0026     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class AuthExceptionEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            map.put("code", 401);//401
            map.put("msg", "无效的token");
        } else {
            map.put("code", 401);//401
            map.put("msg", "访问此资源需要完全的身份验证");
        }
        map.put("data", authException.getMessage());
        map.put("success", false);
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
