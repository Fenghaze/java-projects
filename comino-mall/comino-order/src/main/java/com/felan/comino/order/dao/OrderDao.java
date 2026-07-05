package com.felan.comino.order.dao;

import com.felan.comino.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 11:57:03
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
