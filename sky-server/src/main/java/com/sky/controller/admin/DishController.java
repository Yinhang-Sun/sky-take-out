package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @Autowired
    private RedisTemplate redisTemplate;

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

        //clean cache
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
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

        //clean all dish redis cache data, all key started with dish_
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * Query dish by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Query dish by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("Query dish by id: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * Update dish
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update dish")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("Update dish: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //clean all dish redis cache data, all key started with dish_
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * Start or stop sale dish
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop sale dish")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("Start or stop sale dish: {}", id);
        dishService.startOrStop(status, id);

        //clean all dish redis cache data, all key started with dish_
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * Query dishes by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Query dishes by category id")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("Query dishes by category id: {}", categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * Clean redis cache
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
