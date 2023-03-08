package com.syc.springboot_ford.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syc.springboot_ford.entity.Commodity;
import com.syc.springboot_ford.entity.User;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service
 * @Author: syc
 * @CreateTime: 2023-02-24  14:37
 * @Description: 商品Service接口
 * @Version: 1.0
 */
public interface CommodityService extends IService<Commodity>{
    /**
     * @description: 新增用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [commodity]
     * @return: int
     **/
    int saveInt(Commodity commodity);

    /**
     * @description: 更改用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [commodity]
     * @return: int
     **/
    int update(Commodity commodity);

    /**
     * @description: 删除用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [id]
     * @return: int
     **/
    int delete(Long id);

    /**
     * @description: 分页查询
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [pagNum, pageSize, search]
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.syc.springboot_ford.entity.Commodity>
     **/
    Page<Commodity> findPage(Integer pagNum, Integer pageSize, String search);
}
