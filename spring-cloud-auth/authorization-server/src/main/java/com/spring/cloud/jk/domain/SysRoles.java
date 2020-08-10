package com.spring.cloud.jk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Administrator
 */
public class SysRoles implements GrantedAuthority {

    private String userName;
    private String roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roles;
    }
}
