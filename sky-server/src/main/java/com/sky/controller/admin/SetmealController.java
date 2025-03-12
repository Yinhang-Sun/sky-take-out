package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Setmeal management
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "Setmeal API")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    /**
     * Add new setmeal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new setmeal")
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId") //key: setmealCache::100
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("Add new setmeal: {}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * Setmeal pagination query
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Setmeal pagination query")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("Setmeal pagination query: {}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Delete setmeal in batch
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete setmeal in batch")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Delete setmeal in batch: {}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Query setmeal based on id, used to edit the page with echo data
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Get setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("Get setmeal by id: {}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * Update setmeal
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update setmeal")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("Update setmeal: {}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * Start or stop sale setmeal
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop setmeal")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("Start or stop setmeal: {}", id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
