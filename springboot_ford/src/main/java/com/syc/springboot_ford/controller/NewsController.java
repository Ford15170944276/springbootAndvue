package com.syc.springboot_ford.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syc.springboot_ford.common.Result;
import com.syc.springboot_ford.entity.News;
import com.syc.springboot_ford.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.controller
 * @Author: syc
 * @CreateTime: 2023-03-03  20:50
 * @Description: 控制类
 * @Version: 1.0
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    NewsService newsService;


    @PostMapping
    public Result<?> save(@RequestBody News news) {
        int su = newsService.saveInt(news);
        if (su <= 0) {
            return Result.error("1", "添加失败");
        }
        return Result.success("0", "添加成功");
    }

    @PutMapping
    public Result<?> update(@RequestBody News news) {
        int su = newsService.update(news);
        if (su <= 0) {
            return Result.error("1", "修改失败");
        }
        return Result.success("0", "修改成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int su = newsService.delete(id);
        if (su <= 0) {
            return Result.error("1", "删除失败");
        }
        return Result.success("0", "删除成功");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10")  Integer pageSize,
                              @RequestParam(defaultValue = "")  String search) {

        Page<News> newsPage = newsService.findPage(pageNum,pageSize,search);
        if (newsPage.getSize() <= 0) {
            return Result.error("1", "查询失败");
        }
        return Result.success(newsPage);
    }
}
