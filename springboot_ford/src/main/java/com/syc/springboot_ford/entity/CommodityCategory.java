package com.syc.springboot_ford.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.entity
 * @Author: syc
 * @CreateTime: 2023-03-08  17:20
 * @Description: 商品分类类别表
 * @Version: 1.0
 */
@Data
@TableName("commodity_category")
public class CommodityCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;

    //分类一级名称
    private String category;

    // 商品分类子类id
    private Integer categorySubclassid;
}
