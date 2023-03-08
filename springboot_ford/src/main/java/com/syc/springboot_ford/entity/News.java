package com.syc.springboot_ford.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.entity
 * @Author: syc
 * @CreateTime: 2023-03-03  20:39
 * @Description: 新闻实体类
 * @Version: 1.0
 */
@Data
@TableName("news")
public class News {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //新闻标题
    private String title;
    //新闻内容
    private String content;
    //新闻作者
    private String author;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;
    //上传管理员
    private String adminTname;
}
