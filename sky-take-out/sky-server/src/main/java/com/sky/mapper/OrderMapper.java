package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Orders;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.core.annotation.Order;

@Mapper
public interface OrderMapper {

    /**
     * 新增订单数据
     * @param orders
     */
    @AutoFill(value = OperationType.INSERT)
    Long insert(Orders orders);

    /**
     * 根据订单号查询订单数据
     * @param outTradeNo
     * @return
     */
    @Select("select * from orders where number = #{outTradeNo}")
    Orders getByNumber(String outTradeNo);

    /**
     * 修改订单数据
     * @param orders
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Orders orders);
}
