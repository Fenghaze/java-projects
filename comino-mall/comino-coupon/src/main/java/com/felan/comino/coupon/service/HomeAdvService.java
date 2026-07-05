package com.felan.comino.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 10:30:18
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

