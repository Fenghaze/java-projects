package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理")
public class DishController {
    final String DISH_CACHE_KEY_PREFIX = "dish_";

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("")
    @ApiOperation(value = "添加菜品接口")
    public Result<Dish> add(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品：{}", dishDTO);
        dishService.add(dishDTO);
        // 清理菜品对应分类的缓存
        redisTemplate.delete(DISH_CACHE_KEY_PREFIX + dishDTO.getCategoryId());
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询接口")
    public Result<PageResult> list(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping("")
    @ApiOperation(value = "批量删除菜品接口")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.delete(ids);
        // 清理所有缓存数据
        clearCache(DISH_CACHE_KEY_PREFIX + "*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询菜品接口")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("查询菜品：{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping("")
    @ApiOperation(value = "修改菜品接口")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.update(dishDTO);
        // 清理所有缓存数据
        clearCache(DISH_CACHE_KEY_PREFIX + "*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改菜品状态接口")
    public Result updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("修改菜品状态：{}", status);
        dishService.updateStatus(status, id);
        // 清理所有缓存数据
        clearCache(DISH_CACHE_KEY_PREFIX + "*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据菜品分类查询菜品接口")
    public Result<List<Dish>> list(@RequestParam Long categoryId) {
        log.info("根据菜品分类查询菜品：{}", categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理redis缓存
     * @param pattern key的匹配模式
     */
    private void clearCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
