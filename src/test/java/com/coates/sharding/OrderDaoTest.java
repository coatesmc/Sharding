package com.coates.sharding;

import com.coates.sharding.dao.orderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderDaoTest
 * @Description TODO
 * @Author mc
 * @Date 9/27/2019 2:27 PM
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingjdbcApplication.class})
public class OrderDaoTest {

    @Autowired
    orderMapper orderMapper;

    @Test
    public void test() {
        for (int i = 2; i < 100; i++) {
            orderMapper.insertOrder(new BigDecimal(i), 1L, "SUCCESS");
        }
    }

    @Test
    public void testSelectOrderByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(384358704369106945L);
        ids.add(384362977890926592L);
        ids.add(384373335774986240L);
        ids.add(384373326098726913L);
        List<Map<String, Object>> mapList = orderMapper.selectOrederByIds(ids);
        for (Map<String, Object> map : mapList) {
            System.out.println("=============================================");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
            }
            System.out.println("=============================================");
        }

    }
}
