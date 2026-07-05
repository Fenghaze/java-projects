package com.felan.comino.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.coupon.entity.CouponSpuRelationEntity;

import java.util.Map;

/**
 * 优惠券与产品关联
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 10:30:18
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

