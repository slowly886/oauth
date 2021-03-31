package com.faw.oauth.config;


import com.faw.oauth.config.handler.BootOAuth2WebResponseExceptionTranslator;
import com.faw.oauth.service.AuthorizationCodeImpl;
import com.faw.oauth.service.ClientDetailsServiceImpl;
import com.faw.oauth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final String PREFIX = "faw:cloud:oauth:";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;
    @Autowired
    AuthorizationCodeImpl authorizationCode;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtTokenStore jwtTokenStore;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    TokenEnhancer tokenEnhancer;

    /**
     * 配置一个客户端
     * <p>
     * 既可以通过授权码方式获取令牌，也可以通过密码方式获取令牌
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置token管理
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);

        endpoints
                .tokenEnhancer(enhancerChain)
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCode).exceptionTranslator(new BootOAuth2WebResponseExceptionTranslator())
                .authenticationManager(authenticationManager).exceptionTranslator(new BootOAuth2WebResponseExceptionTranslator())
                .reuseRefreshTokens(true);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")//已验证的客户端才能请求check_token端点
                .allowFormAuthenticationForClients();
    }

    /**
     * jwt的token存储对象
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置jwt转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomizeToken();
    }

    /**
     * 配置redistoken
     */
//    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(PREFIX);
        return redisTokenStore;
    }

    /*@Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setTokenEnhancer(new CustomizeToken());
        defaultTokenServices.setReuseRefreshToken(true);
        return defaultTokenServices;
    }*/
}
