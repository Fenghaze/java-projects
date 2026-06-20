package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 构造购物车对象
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId(); // 获取当前登录用户id
        shoppingCart.setUserId(userId);
        // 查询该用户下的该菜品或套餐是否在购物车中
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 若商品存在，则更新数量
        if (list != null && !list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumber(cart);
            return;
        }
        // 若商品不存在，则插入一条新商品数据
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        if (dishId != null) { // 添加的是菜品
            // 根据dishId获取相关信息
            Dish dish = dishMapper.selectById(dishId);
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
        } else { // 添加的是套餐
            // 根据setmealId获取相关信息
            Setmeal setmeal = setmealMapper.getById(setmealId);
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setAmount(setmeal.getPrice());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }

    @Override
    public List<ShoppingCart> list() {
        // 根据用户id查看购物车数据
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void clean() {
        // 获取当前用户id
        Long userId = BaseContext.getCurrentId();
        // 删除当前用户下的所有购物车数据
        shoppingCartMapper.deleteByUserId(userId);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId(); // 获取当前登录用户id
        shoppingCart.setUserId(userId);
        // 查询用户购物车数据
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null || !list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.updateNumber(cart);
            // 购物车商品数量为0，则删除该商品数据
            if (cart.getNumber() == 0) {
                shoppingCartMapper.delete(cart.getId());
            }
        }
    }
}
