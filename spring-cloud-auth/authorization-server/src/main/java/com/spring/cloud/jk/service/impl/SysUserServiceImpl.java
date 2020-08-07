package com.spring.cloud.jk.service.impl;

import com.spring.cloud.jk.dao.SysUserDao;
import com.spring.cloud.jk.domain.SysUser;
import com.spring.cloud.jk.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser userByUserName = sysUserDao.getUserByUserName(s);

        return userByUserName;
    }
}
