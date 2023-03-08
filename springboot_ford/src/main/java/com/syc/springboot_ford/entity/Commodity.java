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
 * @CreateTime: 2023-02-24  14:30
 * @Description: 商品表
 * @Version: 1.0
 */
@Data
@TableName("commodity")
public class Commodity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //商品名称
    private String commodityName;
    //商品照片
    private String commodityPhoto;
    //商品价格
    private BigDecimal commodityPrice;
    //商品上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp commodityUptime;
    //商品售出时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp commodityOuttime;
    //商品分类id
    private Integer commodityCategoryId;
    //上架商品用户id
    private Integer commodityBusinessId;
    // 商品状态 1：未卖出 2：已卖出 3：正在支付
    private Integer commodityState;
}
