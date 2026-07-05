package com.felan.comino.member.dao;

import com.felan.comino.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-05 10:45:26
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
