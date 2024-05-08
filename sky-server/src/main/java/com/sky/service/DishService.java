package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * Add dish and corresponding flavors
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * Dish pagination query
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * Delete dishes in batches
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * Get dish by id with corresponding flavors
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * Update dish and related flavors based on dish id
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

}
