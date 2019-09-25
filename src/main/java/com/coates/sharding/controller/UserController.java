package com.coates.sharding.controller;

import com.coates.sharding.entity.User;
import com.coates.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName controller
 * @Description TODO
 * @Author mc
 * @Date 2019/8/28 16:20
 * @Version 1.0
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("save")
    public String save() {
        User user = new User();
        user.setName("dalaoyang");
        user.setCity("beijing");
        userService.insertSelective(user);
        return "AER YOU OK";
    }

    @GetMapping("getAll")
    public Object getAll() {
        return userService.selectAll();
    }

}
