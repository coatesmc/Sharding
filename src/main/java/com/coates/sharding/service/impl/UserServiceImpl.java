package com.coates.sharding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coates.sharding.dao.UserMapper;
import com.coates.sharding.entity.User;
import com.coates.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author mc
 * @Date 2019/8/28 17:09
 * @Version 1.0
 **/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public void insertSelective(User user) {
        long id = userMapper.getMaxId();
        user.setId(++id);
        userMapper.insert(user);
    }

    @Override
    public List selectAll() {
        QueryWrapper queryWrapper =  new QueryWrapper<User>();
        return userMapper.selectList(queryWrapper);
    }
}
