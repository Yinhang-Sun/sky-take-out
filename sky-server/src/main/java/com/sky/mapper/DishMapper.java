package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * Query the quantity of dishes based on category id
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * insert dish data
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * Dish pagination query
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * Query dish based on primary key
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * delelte dish based on dish id
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * update dish dynamically based on dish id
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * Query dishes by category id
     * @param dish
     * @return
     */
    //select * from dish where categoryId = ?
    List<Dish> list(Dish dish);

    /**
     * Query dishes based on setmeal id
     * @param setmealId
     * @return
     */
    @Select("select d.* from dish d left join setmeal_dish s on d.id = s.dish_id where s.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    /**
     * Count the number of dishes based on conditions
     * @param map
     * @return
     */
    Integer countByMap(Map map);

}
