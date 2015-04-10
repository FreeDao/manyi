package com.manyi.iw.soa.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manyi.iw.soa.entity.Seekhouse;
import com.manyi.iw.soa.seekhouse.user.service.UserSeekHouseServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class UserSeekHouseTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private UserSeekHouseServiceI userSeekHouseServiceI;

    static {
        final String params = "{\"houseIds\":\"4,3,\",\"customerId\":\"1\",\"addr\":\"士大夫\",\"usermemo\":\"大师傅\",\"appointmentTime\":\"2014-06-25 12:47\"}";
    }

    @Test
    @Rollback(value = false)
    public void test() {
        List<Seekhouse> list = new ArrayList<Seekhouse>();
        for (Long i = 1L; i < 5; i++) {
            Seekhouse seekhouse = new Seekhouse();
            seekhouse.setAppointmentId(i);
            seekhouse.setHouseId(i);
            seekhouse.setAgentId(i);
            seekhouse.setUserId(i);
            seekhouse.setState(new Byte("1"));
            list.add(seekhouse);
        }


    }
}
