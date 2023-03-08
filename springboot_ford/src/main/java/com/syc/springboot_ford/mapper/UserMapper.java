package com.syc.springboot_ford.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syc.springboot_ford.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.mapper
 * @Author: syc
 * @CreateTime: 2023-02-15  18:37
 * @Description: uesrDao层
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
