package com.syc.springboot_ford.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syc.springboot_ford.common.Result;
import com.syc.springboot_ford.entity.User;
import com.syc.springboot_ford.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-02-15  17:53
 * @Description: 用户控制接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;


    @PostMapping
    public Result<?> save(@RequestBody User user) {
        int su = userService.saveInt(user);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        int su = userService.update(user);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int su = userService.delete(id);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10")  Integer pageSize,
                              @RequestParam(defaultValue = "")  String search,
                              @RequestParam(defaultValue = "1") String category) {

        Page<User> userPage = userService.findPage(pageNum, pageSize, search, category);
        if (userPage.getSize() <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success(userPage);
    }

}
