package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * Insert order detail data in batches
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);

}
