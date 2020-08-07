package com.spring.cloud.jk.config;


import com.spring.cloud.jk.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;


@Configuration
@ConfigurationProperties("res.path")
public class ResConfig {

    private String publicKeypath;
    private String privateKeypath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     *
     * @param privateKey  私钥键值
     */
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @PostConstruct
    private void createKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicKeypath);
        privateKey = RsaUtils.getPrivateKey(privateKeypath);
    }


    public String getPublicKeypath() {
        return publicKeypath;
    }

    public void setPublicKeypath(String publicKeypath) {
        this.publicKeypath = publicKeypath;
    }

    public String getPrivateKeypath() {
        return privateKeypath;
    }

    public void setPrivateKeypath(String privateKeypath) {
        this.privateKeypath = privateKeypath;
    }
}
