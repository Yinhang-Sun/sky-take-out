package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
