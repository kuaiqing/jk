package com.spring.cloud.jk.dao;

import com.spring.cloud.jk.domain.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserDao  extends Mapper<SysUser> {

    /**
     * 查询数据
     *userName 23
     * @param
     * @return 影响行数
     */
    @Select("SELECT password,username userName,role roles FROM cc WHERE username = #{username}")
//    @Results({
//            @Result(id=true,property = "userName",column = "username"),
//            @Result(property = "roles",column = "username",javaType = List.class,
//                    many = @Many(select = "com.kk.spring.security.authtication.dao.SysRolesDao.getRolesByUserName"))
//    })
    SysUser getUserByUserName(String userName);
}
