package com.felan.comino.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 11:57:03
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

