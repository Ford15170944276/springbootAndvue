package com.syc.springboot_ford.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syc.springboot_ford.common.Result;
import com.syc.springboot_ford.entity.Commodity;
import com.syc.springboot_ford.entity.User;
import com.syc.springboot_ford.service.CommodityService;
import com.syc.springboot_ford.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-02-24  14:44
 * @Description: 商品控制类
 * @Version: 1.0
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Resource
    CommodityService commodityService;


    @PostMapping
    public Result<?> save(@RequestBody Commodity commodity) {
        int su = commodityService.saveInt(commodity);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Commodity commodity) {
        int su = commodityService.update(commodity);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int su = commodityService.delete(id);
        if (su <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10")  Integer pageSize,
                              @RequestParam(defaultValue = "")  String search) {

        Page<Commodity> commodityPage = commodityService.findPage(pageNum,pageSize,search);
        if (commodityPage.getSize() <= 0) {
            return Result.error("1", "失败");
        }
        return Result.success(commodityPage);
    }
}
