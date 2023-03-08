package com.syc.springboot_ford.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syc.springboot_ford.entity.Admin;
import com.syc.springboot_ford.entity.User;
import com.syc.springboot_ford.mapper.AdminMapper;
import com.syc.springboot_ford.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service.impl
 * @Author: syc
 * @CreateTime: 2023-02-20  18:26
 * @Description: Admin的Service的实现类
 * @Version: 1.0
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    AdminMapper adminMapper;


    /**
     * @description: 登录并记录登录时间
     * @author: syc
     * @date: 2023/2/23 20:07
     * @param: [admin]
     * @return: com.syc.springboot_ford.entity.Admin
     **/
    @Override
    public Admin login(Admin admin) {
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getAdminUsername, admin.getAdminUsername())
                .eq(Admin::getAdminPassword, admin.getAdminPassword()));
        if (res != null){
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            res.setLogintime(Timestamp.valueOf(dateFormat.format(date)));
            adminMapper.updateById(res);
            res = adminMapper.selectById(res.getId());
        }
        return res;
    }

    @Override
    public int register(Admin admin) {
        int su = 0;
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getAdminUsername, admin.getAdminUsername()));
        if (res == null){
            admin.setLv("2");
            su = adminMapper.insert(admin);
        }
        return su;
    }

    @Override
    public int update(Admin admin) {
        int su = adminMapper.updateById(admin);
        return su;
    }

    @Override
    public Admin selectById(Integer id) {
        Admin su = adminMapper.selectById(id);
        return su;
    }
}
