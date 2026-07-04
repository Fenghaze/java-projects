package com.felan.comino.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-04 14:41:21
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

