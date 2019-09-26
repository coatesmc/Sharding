package com.coates.sharding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coates.sharding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author mc
 * @Date 2019/8/28 16:10
 * @Version 1.0
 **/
@Mapper
public interface  UserMapper extends BaseMapper<User> {

    @Select("select max(id) from user")
    long getMaxId();

//    @Insert({""})
    int insert(User user);
}
