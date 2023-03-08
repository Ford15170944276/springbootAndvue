package com.syc.springboot_ford.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.syc.springboot_ford.entity.User;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service
 * @Author: syc
 * @CreateTime: 2023-02-15  18:38
 * @Description:
 * @Version: 1.0
 */
public interface UserService extends IService<User> {
    /**
     * @description: 新增用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [user]
     * @return: int
     **/
    int saveInt(User user);

    /**
     * @description: 更改用户
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [user]
     * @return: int
     **/
    int update(User user);

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
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.syc.springboot_ford.entity.User>
     **/
    Page<User> findPage(Integer pagNum, Integer pageSize, String search, String category);
}
