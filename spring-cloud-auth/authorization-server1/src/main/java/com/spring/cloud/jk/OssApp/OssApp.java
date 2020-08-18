package com.spring.cloud.jk.OssApp;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OssApp implements ClientDetails {
    private String appId;
    private String secret;
    private String role;
    private Set<String> scope;
    private Set<String> types;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Map<String, Object> additionalInformation;
    private Set<String> registeredRedirectUris;
    private List<GrantedAuthority> authorities;

    //访问时间
    private Integer accessTokenTime = 7200;
    //刷新时间
    private Integer refreshTokenTime = 7200;
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    @Override
    public String getClientId() {
        return appId;
    }
    @Override
    public String getClientSecret() {
        return secret;
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    /**
     * new SimpleGrantedAuthority(role)
     * @param authorities
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new ArrayList(authorities);
    }
    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }
    /**
     * values ["authorization_code", "password", "refresh_token", "implicit"]
     * @return
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.types;
    }
    public void setAuthorizedGrantTypes(Set<String> types){
        this.types = types;
    }
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenTime;
    }
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenTime;
    }
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }
    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }
    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris == null?null:new LinkedHashSet(registeredRedirectUris);
    }
    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }
    /**
     * values ["read", "write"]
     * @return
     */
    @Override
    public Set<String> getScope() {
        return this.scope;
    }
    public void setScope(Set<String> scope){
        this.scope = scope;
    }
    @Override
    public boolean isAutoApprove(String scope) {
        if(this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator();
            String auto;
            do {
                if(!var2.hasNext()) {
                    return false;
                }
                auto = (String)var2.next();
            } while(!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }
    @Override
    public boolean isSecretRequired() {
        return this.secret != null;
    }
    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }
}

