package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /**
     * Add dish and corresponding flavors
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
