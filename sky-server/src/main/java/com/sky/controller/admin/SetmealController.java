package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Delete setmeal in batch: {}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}
