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
 * @CreateTime: 2023-03-08  16:26
 * @Description: 商品订单实体类
 * @Version: 1.0
 */
@Data
@TableName("commodity_order")
public class CommodityOrder {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //总价
    private BigDecimal totalPrice;
    //实付款
    private BigDecimal payPrice;
    //优惠金额
    private BigDecimal discount;
    //运费
    private BigDecimal transportPrice;
    //订单编号
    private String orderNo;
    //商家id
    private Integer commodityId;
    //购买用户id
    private Integer userId;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
    //支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp payTime;
    //订单状态 0: 已取消 1:未支付 2:已支付
    private String state;
}
