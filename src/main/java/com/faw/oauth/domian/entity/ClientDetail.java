package com.faw.oauth.domian.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: ClientDetail
 * @Package com.faw.oauth.dao.oauth
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/25/0025 1:07
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/25/0025     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Data
@TableName(value = "faw_client_details_t")
public class ClientDetail implements ClientDetails {

    private final Integer ACCESSTOKENVALIDITYSECONDS = 3600;
    private final Integer REFRESHTOKENVALIDITYSECONDS = 864000;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authorizedGrantTypes;

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> set = new HashSet<>();
        set.add("all");
        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> set = new HashSet<>(Arrays.asList(StringUtils.split(this.authorizedGrantTypes, ",")));
//        set.add("authorization_code");
//        set.add("password");
//        set.add("refresh_token");
        return set;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> set = new HashSet<>(Arrays.asList(this.getRedirectUri().split(",")));
        return set;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set set = new HashSet();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("none");
        set.add(simpleGrantedAuthority);
        return set;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        //token有效时间
        return ACCESSTOKENVALIDITYSECONDS;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return REFRESHTOKENVALIDITYSECONDS;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
