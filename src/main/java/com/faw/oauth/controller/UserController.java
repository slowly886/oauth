package com.faw.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.faw.oauth.config.ResponseCode;
import com.faw.oauth.domian.ResultVO;
import com.faw.oauth.domian.entity.ThirdUser;
import com.faw.oauth.exception.AuthException;
import com.faw.oauth.service.ClientDetailsServiceImpl;
import com.faw.oauth.service.ThirdUserBoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: UserController
 * @Package com.faw.oauth.controller
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/23/0023 20:07
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/23/0023     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    TokenEndpoint tokenEndpoint;
    @Autowired
    AuthorizationEndpoint authorizationEndpoint;
    @Autowired
    CheckTokenEndpoint checkTokenEndpoint;
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;
    @Autowired
    ThirdUserBoundService thirdUserBoundService;

    @RequestMapping("r1")
    public Object re1(HttpServletRequest request) {
        String code = request.getParameter("code");
        return code;
    }

    @RequestMapping("r2")
    public Object re2(HttpServletRequest request, Principal principal) {
        return principal;
    }

    @RequestMapping("/oauth/token")
    public Object over(Principal principal, @RequestParam Map<String, String> parameters) {
        log.info("【授权码获取token】请求入参：" + JSON.toJSONString(parameters));
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = null;
        try {
            oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, parameters);
        } catch (Exception e) {
            log.info("授权码获取token错误");
            return new ResultVO(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getDesc());
        }
        return custom(oAuth2AccessTokenResponseEntity.getBody());
    }

    @RequestMapping("/oauth/authorize")
    public ModelAndView aover(Map<String, Object> model, @RequestParam Map<String, String> parameters, SessionStatus sessionStatus, Principal principal, HttpServletResponse response) throws HttpRequestMethodNotSupportedException, IOException, AuthException {
        log.info("【获取授权码】请求入参：" + JSON.toJSONString(parameters));
        try {
            return authorizationEndpoint.authorize(model, parameters, sessionStatus, principal);
        } catch (Exception e) {
            log.info("获取授权码错误");
            throw new AuthException();
        }
    }

    @RequestMapping("/oauth/check_token")
    public ResultVO checkToken(String token) throws HttpRequestMethodNotSupportedException, IOException, AuthException {
        log.info("【校验token】请求入参：" + token);
        Map<String, ?> map = null;
        try {
            map = checkTokenEndpoint.checkToken(token);
        } catch (Exception e) {
            log.info("校验token错误");
            throw new AuthException();
        }
        return new ResultVO(ResponseCode.SUCCESS_200);
    }

    @PostMapping("/ouath/user/bound")
    public ResultVO userBound(Long user_id, String hw_user_id, String client_id) {
        thirdUserBoundService.userBound(user_id, hw_user_id, client_id);
        return new ResultVO(ResponseCode.SUCCESS_200);
    }

    @PostMapping("/oauth/user/remove")
    public ResultVO userRemove(Long user_id, String hw_user_id, String client_id) {
        thirdUserBoundService.userRemove(user_id, hw_user_id, client_id);
        return new ResultVO(ResponseCode.SUCCESS_200);
    }

    @PostMapping("/query_third_user")
    public ResultVO query(Long user_id, String client_id) {
        ThirdUser thirdUser = thirdUserBoundService.query(user_id, client_id);
        return new ResultVO(thirdUser);
    }

    //自定义返回格式
    private ResultVO custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap(token.getAdditionalInformation());
        data.put("accessToken", token.getValue());
        data.put("expiresIn", token.getExpiresIn());
        if (token.getRefreshToken() != null) {
            data.put("refreshToken", token.getRefreshToken().getValue());
        }
        return new ResultVO(data);
    }
}
