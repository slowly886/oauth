package com.faw.oauth.config;

import com.faw.oauth.config.handler.AuthExceptionEntryPointHandler;
import com.faw.oauth.config.handler.CustomAccessDeniedHandler;
import com.faw.oauth.config.handler.LoginFailHandler;
import com.faw.oauth.config.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 跨域伪造请求限制=无效 */
        http.csrf().disable();
        /* 开启授权认证 */
        http.authorizeRequests()
                //允许访问授权接口
                .antMatchers("/oauth/token", "/oauth/check_token","/query_third_user").permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling()
                //内部错误处理
                .authenticationEntryPoint(new AuthExceptionEntryPointHandler())
                //权限不足处理
                .accessDeniedHandler(new CustomAccessDeniedHandler());
        /* 登录配置 */
        http.formLogin().permitAll()
                //登录成功处理
                .successHandler(new LoginSuccessHandler())
                //登录失败
                .failureHandler(new LoginFailHandler())
                /* session 设置为 IF_REQUIRED 有需要才生成 */
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //增加自定义拦截器
                .and().addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 授权服务配置需要用到这个 bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置密码加密为 不加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
