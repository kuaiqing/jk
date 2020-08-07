package com.spring.cloud.jk.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 创建密钥实体类
 * @param <T>
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
