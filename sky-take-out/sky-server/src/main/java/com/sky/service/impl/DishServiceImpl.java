package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional // 事务注解：保证数据一致性
    @Override
    public void add(DishDTO dishDTO) {
        // 菜品表插入1条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        // dishMapper设置useGeneratedKeys="true" keyProperty="id"，执行完sql后，可返回菜品的id
        Long dishId = dish.getId();
        // 口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        // 将返回结果封装到分页插件的Page中，dao层的list方法无需再实现分页sql
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        // 启用的菜品无法删除
        for (Long id : ids) {
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 套餐内的菜品无法删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishId(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 级联删除：菜品和对应口味需要一起删除
        dishMapper.batchDelete(ids);
        dishFlavorMapper.batchDeleteByDishIds(ids);
    }

    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.selectById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(id);
        // 组装数据
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public void update(DishDTO dishDTO) {
        // 修改菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        Long dishId = dish.getId();
        // 删除菜品对应的口味表
        dishFlavorMapper.deleteByDishId(dishId);
        // 添加菜品对应的口味表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish = dishMapper.selectById(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
}
