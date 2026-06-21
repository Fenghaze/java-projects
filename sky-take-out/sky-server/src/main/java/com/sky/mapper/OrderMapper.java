package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.enumeration.OperationType;
import com.sky.vo.OrderVO;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    Page<OrderVO> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单详情
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     * @param statusCode
     * @return
     */
    @Select("select count(id) from orders where status = #{statusCode}")
    Integer countStatus(Integer statusCode);

    /**
     * 查询超时订单
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{time}")
    List<Orders> getOvertimeOrders(@Param("status") Integer status, @Param("time") LocalDateTime time);

    /**
     * 统计当天营业额
     * @param status 订单状态
     * @param begin
     * @param end
     * @return 营业额
     */
    Double turnoverStatistics(@Param("status") Integer status, @Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);
}
