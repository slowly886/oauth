package com.faw.oauth.domian.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.cusc.com
 *
 * @author yanke
 * @Title: User
 * @Package com.faw.oauth.domian
 * @Description:类描述
 * @Email yanke@cu-sc.com
 * @version: v1.0.0
 * @date: 2020/2/23/0023 16:04
 * @Copyright: 2020  www.faw.com.cn Inc. All rights reserved. <br/>
 * 注意:本内容仅限于一汽集团内部传阅,禁止外泄以及用于其他的商业目的
 * Modification History:<br/>
 * Date         Author          Version            Description<br/>
 * *---------------------------------------------------------*<br/>
 * 2020/2/23/0023     Administrator           v1.0.0               修改原因<br/>
 * @since JDK 1.8
 */
@Data
@TableName(value = "faw_user_t")
public class User implements UserDetails {

    private Long id;
    private String userName;
    private String userPassword;
    private String userMobileno;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set set = new HashSet();
        Grant grant = new Grant();
        set.add(grant);
        return set;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
