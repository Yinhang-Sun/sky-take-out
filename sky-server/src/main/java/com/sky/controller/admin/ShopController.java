package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "Shop API")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Set shop status
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("Set shop status")
    public Result SetStatus(@PathVariable Integer status) {
        log.info("Shop status:{}", status == 1 ? "Open" : "Closed");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * Get shop status
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("Get shop status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("Get shop status: {}", status == 1 ? "Open" : "Closed");
        return Result.success(status);
    }
}
