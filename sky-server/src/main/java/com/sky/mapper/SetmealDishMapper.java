package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * query setmeal ids based on dish ids
     * @param dishIds
     * @return
     */
    //select setmeal_id from setmeal_dish where dish_id in (1, 2, 3, 4);
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * save the relationship of setmeal and dishes in batches
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

}
