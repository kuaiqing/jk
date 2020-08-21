package com.spring.cloud.jk.config;

import com.spring.cloud.jk.component.JwtTokenEnhancer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private  PasswordEncoder passwordEncoder;
//    @Autowired
//    private  UserDetailsService userDetailsService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
//    private  JwtTokenEnhancer jwtTokenEnhancer;
    private TokenStore jwtTokenStore;

    private  Integer accessTokenTime = 3600;
    private  Integer refreshTokenTime = 86400;

    public Oauth2ServerConfig() {
        super();
    }
    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * Spring Security OAuth2会公开了两个端点，
     * 用于检查令牌（/oauth/check_token和/oauth/token_key），
     * 这些端点默认受保护denyAll()。
     * tokenKeyAccess（）和checkTokenAccess（）方法会打开这些端点以供使用
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()");

    }

    /**
     * 配置客户端信息
     * clientId - （必需）客户端ID。
     * secret - （可信客户端所需）客户端密钥（可选）。
     * scope - 客户受限的范围。如果范围未定义或为空（默认值），则客户端不受范围限制。
     * authorizedGrantTypes - 授权客户端使用的授权类型。默认值为空。
     * authorities - 授予客户的权限（常规Spring Security权限）。
     * redirectUris - 将用户代理重定向到客户端的重定向端点。它必须是绝对URL。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .accessTokenValiditySeconds(accessTokenTime)
                .refreshTokenValiditySeconds(refreshTokenTime)
                .redirectUris("www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> delegates = new ArrayList<>();
//        delegates.add(jwtTokenConfig);
//        delegates.add(accessTokenConverter());
        //配置JWT的内容增强器
//        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
//                //配置加载用户信息的服务
//                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter())
//                .tokenEnhancer(enhancerChain);
        .tokenStore(jwtTokenStore);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }
}
