package com.felan.comino.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 11:57:03
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

