package com.sky.mapper;

import com.sky.entity.OrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单详情数据
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);
}
