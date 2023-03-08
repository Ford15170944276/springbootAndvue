package com.syc.springboot_ford.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syc.springboot_ford.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.mapper
 * @Author: syc
 * @CreateTime: 2023-02-20  18:22
 * @Description: admin dao层
 * @Version: 1.0
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
