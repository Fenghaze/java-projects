package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    static final String DISH_CACHE_KEY_PREFIX = "dish_";
    static final long DISH_CACHE_KEY_EXPIRE_TIME = 60 * 60;

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        String cacheKey = DISH_CACHE_KEY_PREFIX + categoryId;
        // 查询缓存
        List<DishVO> cacheList = (List<DishVO>) redisTemplate.opsForValue().get(cacheKey);
        if (cacheList != null && !cacheList.isEmpty()) {
            log.info("查询分类菜品缓存信息cachelist={}", cacheList);
            return Result.success(cacheList);
        }
        // 若缓存不存在，则查询数据库，并设置缓存
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<DishVO> list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(cacheKey, list, DISH_CACHE_KEY_EXPIRE_TIME, TimeUnit.SECONDS);
        return Result.success(list);
    }

}
