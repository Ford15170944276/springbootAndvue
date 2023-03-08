package com.syc.springboot_ford.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syc.springboot_ford.entity.Commodity;
import com.syc.springboot_ford.entity.User;
import com.syc.springboot_ford.mapper.CommodityMapper;
import com.syc.springboot_ford.service.CommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service.impl
 * @Author: syc
 * @CreateTime: 2023-02-24  14:38
 * @Description: 商品service处理层
 * @Version: 1.0
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
    @Resource
    CommodityMapper commodityMapper;

    @Override
    public int saveInt(Commodity commodity) {
        int su = commodityMapper.insert(commodity);
        return su;
    }

    @Override
    public int update(Commodity commodity) {
        int su = commodityMapper.updateById(commodity);
        return su;
    }

    @Override
    public int delete(Long id) {
        int su = commodityMapper.deleteById(id);
        return su;
    }

    @Override
    public Page<Commodity> findPage(Integer pagNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Commodity> wrapper = Wrappers.<Commodity>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Commodity::getCommodityName, search);
        }
        Page<Commodity> commodityPage = commodityMapper.selectPage(new Page<>(pagNum, pageSize), wrapper);
        return commodityPage;
    }
}
