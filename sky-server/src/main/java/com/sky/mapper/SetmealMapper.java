package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * Query the quantity of setmeal based on category id
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * update setmeal based on id
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * insert setmeal data
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * Setmeal pagination query
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * query setmeal by id
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * delete setmeal from setmeal table
     * @param setmealId
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long setmealId);

    /**
     * Query setmeal by dynamic conditions
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * Count the number of setmeal based on conditions
     * @param map
     * @return
     */
    Integer countByMap(Map map);

}
