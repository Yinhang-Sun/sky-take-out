package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * category
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "Category related interface")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * add category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("Add category：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * category pagination query
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Category pagination query")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("Category pagination query：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * delete category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete category")
    public Result<String> deleteById(Long id){
        log.info("Delete category：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * update category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * enable or disable category
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Enable or disable category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * query category by type
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Query category by type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
