package com.felan.comino.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-04 14:41:21
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

