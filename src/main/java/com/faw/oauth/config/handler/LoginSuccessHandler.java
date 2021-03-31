package com.faw.oauth.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.faw.oauth.domian.ResultVO;
import com.faw.oauth.domian.entity.User;
import com.faw.oauth.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: LoginSuccessHandler
 * @Package com.faw.oauth.config.handler
 * @Description:用户登录成功处理操作
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/3/3/0003 15:41
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/3/3/0003     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String username = request.getParameter("username");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorities", user.getAuthorities());
        jsonObject.put("user_name", user.getUsername());
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String compact = Jwts.builder()
                .setClaims(jsonObject)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 60000L))// 自定义属性
                .signWith(signatureAlgorithm, "secret".getBytes(StandardCharsets.UTF_8)).compact();

        JSONObject data = new JSONObject();
        data.put("accessToken", compact);
        ResultVO resultVO = new ResultVO(data);
        response.setHeader(JwtUtils.HEADER_TOKEN_NAME, compact);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONStringWithDateFormat(resultVO, JSON.DEFFAULT_DATE_FORMAT));
        out.flush();
        out.close();
    }
}
