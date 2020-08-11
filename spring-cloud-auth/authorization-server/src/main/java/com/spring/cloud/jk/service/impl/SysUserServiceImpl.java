package com.spring.cloud.jk.service.impl;

import com.spring.cloud.jk.dao.SysUserDao;
import com.spring.cloud.jk.domain.SysRoles;
import com.spring.cloud.jk.domain.SysUser;
import com.spring.cloud.jk.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String encode = passwordEncoder.encode("123456");
        log.info("密码{}"+encode);
        SysUser userByUserName = sysUserDao.getUserByUserName(s);
        SysRoles sysRoles = new SysRoles();
        sysRoles.setRoles("ROLE_USER");
        List<SysRoles> ss = new ArrayList<>();
        ss.add(sysRoles);
        userByUserName.setRoles(ss);
        log.info("用户对象{}"+userByUserName);
        return userByUserName;
    }
}
