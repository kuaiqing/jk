package com.spring.cloud.jk.service;


import com.spring.cloud.jk.OssApp.OssApp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class OssClientDetailsService implements ClientDetailsService {

    @Autowired
    private OssApp app;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger log = Logger.getLogger(OssClientDetailsService.class);
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.warn("loadClientByClientId");
        app.setAppId("demoApp");
        app.setSecret(passwordEncoder.encode("demoAppSecret"));
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("READ"));
        authorities.add(new SimpleGrantedAuthority("WRITE"));
        app.setAuthorities(authorities);
        // 授权类型
        Set<String> authorizedGrantTypes = new TreeSet<String>();
        authorizedGrantTypes.add("password");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("authorization_code");
        app.setAuthorizedGrantTypes(authorizedGrantTypes);
        Set<String> scope = new TreeSet<String>();
        scope.add("openid");
        app.setScope(scope);
        return app;
    }
}
