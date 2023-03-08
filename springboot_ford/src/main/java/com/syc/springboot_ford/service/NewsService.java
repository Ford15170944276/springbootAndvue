package com.syc.springboot_ford.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syc.springboot_ford.entity.Commodity;
import com.syc.springboot_ford.entity.News;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service
 * @Author: syc
 * @CreateTime: 2023-03-03  20:41
 * @Description: 新闻service层
 * @Version: 1.0
 */
public interface NewsService extends IService<News> {
    /**
     * @description: 新增用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [news]
     * @return: int
     **/
    int saveInt(News news);

    /**
     * @description: 更改用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [news]
     * @return: int
     **/
    int update(News news);

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
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.syc.springboot_ford.entity.News>
     **/
    Page<News> findPage(Integer pagNum, Integer pageSize, String search);
}