package com.spring.cloud.jk.filter;


import com.spring.cloud.jk.config.ResConfig;
import com.spring.cloud.jk.domain.SysRoles;
import com.spring.cloud.jk.domain.SysUser;
import com.spring.cloud.jk.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ResConfig prop;

    public JwtLoginFilter(AuthenticationManager authenticationManager, ResConfig prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
         SysUser sysUser = new SysUser();
        try {
            //获取当前请求中的参数
//            SysUser sysUser = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            sysUser.setUserName(username);
            sysUser.setPassword(password);
            //封装对象
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
//            return authRequest;
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                Map ap = new HashMap();
                ap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                ap.put("msg", "用户名或者密码错误");
                writer.println(ap);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SysUser sysUser = new SysUser();
        sysUser.setRoles((List<SysRoles>) authResult.getAuthorities());
        sysUser.setUserName(authResult.getName());
        //生成token
        String s = JwtUtils.generateTokenExpireInSeconds(sysUser, prop.getPrivateKey(), 2 * 60);
        //将token放入响应头
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Authorization", "Bearer "+s);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            Map ap = new HashMap();
            ap.put("code", HttpServletResponse.SC_OK);
            ap.put("msg", "登录成功");
            ap.put("Authorization", "Bearer "+s);
            writer.println(ap);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
