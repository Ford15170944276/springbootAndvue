package com.syc.springboot_ford.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syc.springboot_ford.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.mapper
 * @Author: syc
 * @CreateTime: 2023-02-24  14:36
 * @Description: 商品dao接口
 * @Version: 1.0
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
}
