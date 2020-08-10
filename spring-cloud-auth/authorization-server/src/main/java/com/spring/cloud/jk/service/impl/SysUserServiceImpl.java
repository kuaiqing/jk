package com.spring.cloud.jk.service.impl;

import com.spring.cloud.jk.dao.SysUserDao;
import com.spring.cloud.jk.domain.SysUser;
import com.spring.cloud.jk.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser userByUserName = sysUserDao.getUserByUserName(s);
        log.info("用户对象{}"+userByUserName);
        return userByUserName;
    }
}
