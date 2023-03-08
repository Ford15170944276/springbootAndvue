package com.syc.springboot_ford.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syc.springboot_ford.entity.Admin;

import java.util.List;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service
 * @Author: syc
 * @CreateTime: 2023-02-20  18:24
 * @Description:
 * @Version: 1.0
 */
public interface AdminService extends IService<Admin> {
    /**
     * @description: 管理员登录
     * @author: syc
     * @date: 2023/2/20 18:31
     * @param: [admin]
     * @return: com.syc.springboot_ford.entity.Admin
     **/
    Admin login(Admin admin);

    /**
     * @description: 注册
     * @author: syc
     * @date: 2023/2/21 17:48
     * @param: [admin]
     * @return: int
     **/
    int register(Admin admin);


    /**
     * @description: 更改用户
     * @author: syc
     * @date: 2023/2/23 11:31
     * @param: [admin]
     * @return: int
     **/
    int update(Admin admin);

    /**
     * @description: 根据id查找管理员
     * @author: syc
     * @date: 2023/3/3 20:42
     * @param: [id]
     * @return: com.syc.springboot_ford.entity.Admin
     **/
    Admin selectById(Integer id);
}
