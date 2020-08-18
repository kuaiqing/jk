//package com.spring.cloud.jk.config;
//
//
////import com.spring.cloud.jk.handler.SecurityAuthenticationFailHandler;
////import com.spring.cloud.jk.handler.SecurityAuthenticationSuccessHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
///**
// * @author Administrator
// * configure(HttpSecurity http) httpSecurity中配置所有请求的安全验证
// * 注入Bean UserDetailsService
// * 注入Bean AuthenticationManager 用来做验证
// * 注入Bean PasswordEncoder
// *
// */
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebServerSecurityConfig extends WebSecurityConfigurerAdapter {
////    @Autowired
////    SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;
////    @Autowired
////    SecurityAuthenticationFailHandler securityAuthenticationFailHandler;
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("demoUser1").password(passwordEncoder().encode("123456")).authorities(
//                "USER").build());
//        manager.createUser(User.withUsername("demoUser2").password(passwordEncoder().encode("123456")).authorities("USER").build());
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        AuthenticationManager manager = super.authenticationManagerBean();
//        return manager;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http
//                .requestMatchers().antMatchers("/oauth/**","/login/**","/logout/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**").authenticated()
//                .and()
//                .formLogin().permitAll();
////                .successHandler(securityAuthenticationSuccessHandler)
////                .failureHandler(securityAuthenticationFailHandler);
//    }
//}
//
