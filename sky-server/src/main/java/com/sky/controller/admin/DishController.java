package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dish management
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish API")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Add new dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("Add new dish: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * Dish pagination query
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Dish pagination query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Dish pagination query: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Delete dish in batches
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete dish in batches")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Delete dish in batches: {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
}
