package com.faw.oauth.config;

import com.alibaba.fastjson.JSON;
import com.faw.oauth.domian.ResultVO;
import com.faw.oauth.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: JWTAuthenticationFilter
 * @Package com.faw.oauth.config
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/27/0027 1:38
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/27/0027     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(JwtUtils.HEADER_TOKEN_NAME);
        token = StringUtils.removeStart(token, "Bearer");
        /* token为null直接走登录的过滤器，不为空走下面 */
        if (token != null && token.trim().length() > 0) {
//            String tokenBody = null;
            try {
                Claims body = Jwts.parser().setSigningKey("secret".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(body.get("user_name"), null, null));
//                User user = JSON.parseObject(JSON.toJSONString(body.get("user")), User.class);
//                tokenBody = JwtUtils.testJwt(token);
            } catch (Exception e) {
                ResultVO resultVO = new ResultVO(ResponseCode.TOKEN_ERROR);
                HttpServletResponse res = (HttpServletResponse) response;
                res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                res.getWriter().write(JSON.toJSONStringWithDateFormat(resultVO, JSON.DEFFAULT_DATE_FORMAT));
                return;
            }
            /* 从token中取出用户信息，放在上下文中 */
         /*   if (tokenBody != null && tokenBody.trim().length() > 0) {
                JSONObject user = JSON.parseObject(tokenBody).getJSONObject("user");
                User sysUser = JSON.toJavaObject(user, User.class);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(sysUser, null, sysUser.getAuthorities()));
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                res.getWriter().write("{\"code\": \"405\", \"msg\": \"token错误\"}");
                return;
            }*/
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
