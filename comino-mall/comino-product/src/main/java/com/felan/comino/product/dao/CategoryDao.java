package com.felan.comino.product.dao;

import com.felan.comino.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author felan
 * @email feng_haze@163.com
 * @date 2026-07-04 14:41:20
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
