package com.felan.comino.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.comino.common.utils.PageUtils;
import com.felan.comino.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 10:45:26
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

