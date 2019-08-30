package com.coates.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coates.sharding.entity.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author mc
 * @Date 2019/8/28 16:20
 * @Version 1.0
 **/

public interface UserService  extends IService<User> {

     void insertSelective(User user);

     List<User> selectAll();
}
