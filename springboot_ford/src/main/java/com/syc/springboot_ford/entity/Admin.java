package com.syc.springboot_ford.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.entity
 * @Author: syc
 * @CreateTime: 2023-02-20  16:50
 * @Description: 管理员实体类
 * @Version: 1.0
 */
@Data
@ToString
@TableName("admin")
public class Admin {
    //管理员id
    @TableId(type = IdType.AUTO)
    private int id;
    //管理员用户名
    private String adminUsername;
    //管理员密码
    private String adminPassword;
    //管理员邮箱联系方式
    private String adminEmail;
    //管理员联系方式
    private String adminPhone;
    //管理员真实姓名
    private String adminTname;
    //管理员最后登录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp logintime;
    //管理员账户级别
    private String lv;
}
