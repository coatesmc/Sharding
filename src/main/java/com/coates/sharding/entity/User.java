package com.coates.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author mc
 * @Date 2019/8/28 16:10
 * @Version 1.0
 **/
@Data
@TableName("user")
public class User {
    private Long id;
    private String city;
    private String name;
}
