package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 添加购物车
     * @param shoppingCart
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(ShoppingCart shoppingCart);

    /**
     * 查询购物车数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车数据
     * @param shoppingCart
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(ShoppingCart shoppingCart);

    /**
     * 修改购物车商品数量
     * @param cart
     */
    @AutoFill(value = OperationType.UPDATE)
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumber(ShoppingCart cart);
}
