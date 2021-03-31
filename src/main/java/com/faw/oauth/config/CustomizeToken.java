package com.faw.oauth.config;

import com.faw.oauth.domian.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: CustomizeToken
 * @Package com.faw.oauth.config
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/3/11/0011 16:34
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/3/11/0011     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
public class CustomizeToken implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Object principal = oAuth2Authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> additionalInfo = new HashMap<>();
        if (principal instanceof User) {
            User user = (User) principal;
            additionalInfo.put("id", user.getId());
            additionalInfo.put("userMobileno", user.getUserMobileno());
        }
//        Object principal = oAuth2Authentication.getUserAuthentication().getPrincipal();
//        User user = (User) userDetailsService.loadUserByUsername(principal);
//        user.setUserMobileno("13971098750");
//        additionalInfo.put("id", 215);
//        additionalInfo.put("userMobileno", "133");
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
