package com.felan.comino.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-04 14:41:21
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

