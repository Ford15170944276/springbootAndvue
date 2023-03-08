package com.syc.springboot_ford.controller;

import com.syc.springboot_ford.common.Result;
import com.syc.springboot_ford.config.VerificationConfig;
import com.syc.springboot_ford.entity.Admin;
import com.syc.springboot_ford.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-02-19  17:45
 * @Description: 登录注册控制
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin")
public class LoginController {
    @Resource
    AdminService adminService;

    @Autowired
    private VerificationConfig verificationConfig;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin) {
        Admin login = adminService.login(admin);
        if (login == null) {
            return Result.error("1", "失败,用户名或密码错误");
        }
        return Result.success("0","登录成功", login);
    }

    @PostMapping ("/register/{checkEmail}")
    public Result<?> register(@RequestBody Admin admin,@PathVariable String checkEmail) {
        boolean flag = verificationConfig.getRedisCode(admin.getAdminEmail(),checkEmail);
        if (flag){
            int su = adminService.register(admin);
            if (su == 0) {
                return Result.success("0", "失败,用户名重复错误");
            }
            return Result.success("0","注册成功");
        }
        return Result.error("1", "验证码过期或错误");
    }

    @PutMapping
    public Result<?> update(@RequestBody Admin admin) {
        int login = adminService.update(admin);
        if (login <= 0) {
            return Result.error("1", "失败,用户名或密码错误");
        }
        return Result.success("0","修改成功");
    }

    @GetMapping("/{id}")
    public Result<?> selectById(@PathVariable Integer id) {
        Admin admin = adminService.selectById(id);
        if (admin == null) {
            return Result.error("1", "获取错误");
        }
        return Result.success("0","获取成功", admin);
    }
}
