package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

public interface ShoppingCartService {

    /**
     * add shopping cart
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
