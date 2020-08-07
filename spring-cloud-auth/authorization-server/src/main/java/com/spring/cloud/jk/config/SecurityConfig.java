package com.spring.cloud.jk.config;


import com.spring.cloud.jk.filter.JwtLoginFilter;
import com.spring.cloud.jk.filter.JwtVerifyFilter;
import com.spring.cloud.jk.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;

    @Autowired
    private ResConfig pop;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用户信息的来源
        /*auth.inMemoryAuthentication()
                 .withUser("user")
                 .password("{noop}user")
                 .roles("USER")
                 .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN");*/
        auth.userDetailsService(sysUserServiceImpl).passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/hello","/index","/fail","/logout","/cc/dd")
                .permitAll()
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/hello")
                .loginProcessingUrl("/index")
//                .successForwardUrl("index.html")
                .failureForwardUrl("/fail")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager(),pop))
                .addFilterAt(
                        new JwtVerifyFilter(super.authenticationManager(),pop), BasicAuthenticationFilter.class)
                ;

    }
}
