package com.felan.comino.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 11:58:23
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

