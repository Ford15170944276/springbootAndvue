package com.syc.springboot_ford.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syc.springboot_ford.entity.User;
import com.syc.springboot_ford.mapper.UserMapper;
import com.syc.springboot_ford.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service.impl
 * @Author: syc
 * @CreateTime: 2023-02-15  18:45
 * @Description: User的Service的实现类
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public int saveInt(User user){
        user.setAccount(BigDecimal.valueOf(0));
        user.setCategory("1");
        int su = userMapper.insert(user);
        return su;
    }

    @Override
    public int update(User user){
        int su = userMapper.updateById(user);
        return su;
    }

    @Override
    public int delete(Long id) {
        int su = userMapper.deleteById(id);
        return su;
    }

    @Override
    public Page<User> findPage(Integer pagNum, Integer pageSize, String search, String category){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getCategory, category);
        if (StrUtil.isNotBlank(search)){
            wrapper.like(User::getName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pagNum, pageSize), wrapper);
        return userPage;
    }

}
