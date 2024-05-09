package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {

    /**
     * Add new setmeal, and save the relationship between the setmeal and the dish
     * @param setmealDTO
     * @return
     */
    void saveWithDish(SetmealDTO setmealDTO);

}
