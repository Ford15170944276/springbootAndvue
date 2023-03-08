package com.syc.springboot_ford.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syc.springboot_ford.entity.News;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.mapper
 * @Author: syc
 * @CreateTime: 2023-03-03  20:40
 * @Description: dao层
 * @Version: 1.0
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
