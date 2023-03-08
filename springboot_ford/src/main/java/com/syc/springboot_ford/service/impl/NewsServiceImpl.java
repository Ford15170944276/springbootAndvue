package com.syc.springboot_ford.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syc.springboot_ford.entity.News;
import com.syc.springboot_ford.mapper.NewsMapper;
import com.syc.springboot_ford.service.NewsService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * @BelongsProject: springboot_ford
 * @BelongsPackage: com.syc.springboot_ford.service.impl
 * @Author: syc
 * @CreateTime: 2023-03-03  20:44
 * @Description: 新闻类service实现层
 * @Version: 1.0
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Resource
    NewsMapper newsMapper;

    @Override
    public int saveInt(News news) {
        news.setTime(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
        int su = newsMapper.insert(news);
        return su;
    }

    @Override
    public int update(News news) {
        int su = newsMapper.updateById(news);
        return su;
    }

    @Override
    public int delete(Long id) {
        int su = newsMapper.deleteById(id);
        return su;
    }

    @Override
    public Page<News> findPage(Integer pagNum, Integer pageSize, String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(News::getAuthor, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pagNum, pageSize), wrapper);
        return newsPage;
    }
}
