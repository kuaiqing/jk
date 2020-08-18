package com.spring.cloud.jk.filter;


import com.spring.cloud.jk.config.ResConfig;
import com.spring.cloud.jk.domain.SysUser;
import com.spring.cloud.jk.pojo.Payload;
import com.spring.cloud.jk.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtVerifyFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private ResConfig prop;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, ResConfig prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //获取Authorization值
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            PrintWriter writer = null;
            try {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                writer = httpServletResponse.getWriter();
                Map ap = new HashMap();
                ap.put("code", HttpServletResponse.SC_FORBIDDEN);
                ap.put("msg", "请登录");
                writer.println(ap);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else{
            String bearer_ = authorization.replace("Bearer ", "");
            //验证token是否正确
            Payload<SysUser> infoFromToken = JwtUtils.getInfoFromToken(bearer_, prop.getPublicKey(), SysUser.class);
            SysUser userInfo = infoFromToken.getUserInfo();
            if(userInfo != null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), null,
                        userInfo.getAuthorities());
                SecurityContext context = SecurityContextHolder.getContext();
                System.out.println("-------------"+context);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContext context1 = SecurityContextHolder.getContext();
                System.out.println("-------------"+context1);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }




    }
}
