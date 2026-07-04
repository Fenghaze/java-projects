package com.felan.comino.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.product.entity.CategoryEntity;

import java.util.Map;

/**
 * 商品三级分类
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-04 14:41:20
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

