package com.syc.springboot_ford.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.entity
 * @Author: syc
 * @CreateTime: 2023-02-15  18:01
 * @Description: 用户实体类
 * @Version: 1.0
 */
@ToString
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    //用户id
    private Integer id;
    //用户账户
    private String username;
    //昵称
    private String nickname;
    //真实姓名
    private String name;
    //密码
    private String password;
    //性别
    private String sex;
    //年龄
    private Integer age;
    //出生年月日
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp birth;
    //工作城市
    private String workCity;
    //地址
    private String address;
    //学历
    private String education;
    //电话
    private String phone;
    //邮箱
    private String email;
    //用户头像
    private String avatar;
    //账户余额
    private BigDecimal account;
    //用户类别状态 0：账户被封禁 1：使用中
    private String category;
    //当前去向状态
    private String state;
}
