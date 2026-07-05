package com.felan.comino.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 11:58:23
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

