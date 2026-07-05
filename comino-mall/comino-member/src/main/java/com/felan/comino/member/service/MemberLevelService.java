package com.felan.comino.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 10:45:26
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

