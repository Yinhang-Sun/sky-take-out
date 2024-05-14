package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * Add new setmeal, and save the relationship between the setmeal and the dish
     * @param setmealDTO
     * @return
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * Setmeal pagination query
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * Delete setmeal in batch
     * @param ids
     * @return
     */
    void deleteBatch(List<Long> ids);

    /**
     * Query setmeal based on id, used to edit the page with echo data
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * Update setmeal
     * @param setmealDTO
     * @return
     */
    void update(SetmealDTO setmealDTO);

    /**
     * start or stop sale setmeal
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Query setmeal list
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * Query dish list by setmeal id
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
