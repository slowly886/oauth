package com.faw.oauth.config.handler;

import com.alibaba.fastjson.JSON;
import com.faw.oauth.constants.Constants;
import com.faw.oauth.domian.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: LoginFailHandler
 * @Package com.faw.oauth.config.handler
 * @Description:登录失败处理操作
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/3/3/0003 15:44
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/3/3/0003     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultVO resultVO = new ResultVO(Constants.RETURN_FAIL_CODE, Constants.RETURN_FAIL_MSG);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONStringWithDateFormat(resultVO, JSON.DEFFAULT_DATE_FORMAT));
        out.flush();
        out.close();
    }
}
