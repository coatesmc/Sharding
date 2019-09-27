package com.coates.sharding.dao;
import	java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @ClassName orderMapper
 * @Description TODO
 * @Author mc
 * @Date 9/27/2019 2:21 PM
 * @Version 1.0
 **/
@Mapper
@Component
public interface orderMapper {
    /**
     * 插入功能
     * @param price
     * @param userId
     * @param status
     * @return
     */
    @Insert("INSERT INTO `t_order` (`price`, `user_id`, `status`) VALUES ( #{price},  #{userId},  #{status})")
    int insertOrder(@Param("price") BigDecimal price, @Param("userId") Long userId, @Param("status") String status);


    /**
     * 根据id列表查询订单
     * @param ids
     * @return
     */
    @Select("<script>" +
            "SELECT" +
            "`id`," +
            "`price`," +
            "`user_id`," +
            "`status`" +
            "FROM t_order t where id in " +
            "<foreach close=')'  collection='ids' open='(' separator=','  item='id'> " +
            "#{id}"+
            "</foreach>"+
            "</script>")
    List<Map<String,Object>> selectOrederByIds(@Param("ids") List<Long> ids);
}
