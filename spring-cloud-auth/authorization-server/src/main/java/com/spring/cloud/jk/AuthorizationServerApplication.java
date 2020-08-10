package com.spring.cloud.jk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *

 *          * *【密码授权模式-client】
 *          * 		密码模式需要参数：username,password,grant_type,client_id,client_secret
 *          * 		http://localhost:8080/oauth/token?username=demoUser1&password=123456&grant_type=password&client_id=demoApp&client_secret=demoAppSecret
 *          *
 *          * *【客户端授权模式-password】 客户端模式需要参数：grant_type,client_id,client_secret
 *          * 		http://localhost:8080/oauth/token?grant_type=client_credentials&client_id=demoApp&client_secret=demoAppSecret
 *          *
 *          * *【授权码模式-code】 获取code
 *          * 		http://localhost:8080/oauth/authorize?response_type=code&client_id=demoApp&redirect_uri=http://baidu.com
 *          *
 *          ** 【通过code】 换token
 *          * 		http://localhost:8080/oauth/token?grant_type=authorization_code&code=dI272o&client_id=demoApp&client_secret=demoAppSecret&redirect_uri=http://baidu.com
 *          * 		这里的code字段是授权码模式中返回的code  例如： https://www.baidu.com/?code=tsuHSh
 *          *
 *          ** 【通过refresh token】 刷新token
 *          *		http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=7ba47059-d853-4050-9c64-69d0cade71a7&client_id=demoApp&client_secret=demoAppSecret
 *          *		其中grant_type为固定值：grant_type=refresh_token    , refresh_token = 通过code获取的token中的refresh_token
 *          *
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@MapperScan("com.spring.cloud.jk.dao.SysUserDao")
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class,args);
    }


    @GetMapping (value = "/aa")
    public Object aa(){
        return "aa";
    }
}
