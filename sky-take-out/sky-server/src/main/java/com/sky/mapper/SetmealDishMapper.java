package com.sky.mapper;

import com.sky.entity.SetmealDish;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据多个菜品id查询套餐ids（多对多关系）
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);
}
