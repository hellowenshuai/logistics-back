package com.ansel;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.IRouteService;
import com.ansel.util.Enctype;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author chenshuai
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogisticsApplicationTests {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private IRouteService routeService;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private IRouteInfoDao routeInfoDao;

    @Autowired
    private IGoodsBillDao goodsBillDao;

    @Autowired
    private IFunctionWithGroupDao functionWithGroupDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void test1() {
        User user = userDao.findByLoginId("PJ111879");
        System.err.println(user);
    }

    @Test
    public void test2() {
        Employee employee = employeeDao.findByEmployeeCode("PJ111879");
        System.err.println(employee);
    }

    @Test
    public void test3() {
        String password = Enctype.MD5("123456");
        System.err.println(password);
    }

    @Test
    public void test4() {
        User user = new User();
        user.setLoginId("PJ595668");
        userDao.delete(user);
    }

    @Test
    public void test5() {
        employeeDao.updateDepartment("财务组", "财务xx");
    }

    @Test
    public void test6() {
        routeService.generateRoute();
    }

    @Test
    public void test7() {
        List<Region> regions = regionDao.findLeftRegions();
        System.err.println(regions);
    }

    /**
     * @return void
     * @description如果JPA提示Executing an update/delete query，
     * 那是一定是因为Service层没有加@Transactional和再方法加@Modifying吧。
     * @params []
     * @creator chenshuai
     * @date 2019/3/20 0020
     */
    @Test
    @Transactional
    public void test8() {
        routeInfoDao.truncateTable();
    }

    @Test
    public void test9() {
        // Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        System.err.println(calendar.get(Calendar.MONTH) + 1);
    }

    @Test
    public void test10() {
        List<GoodsBill> list = goodsBillDao.transferState("未到车辆", "SJ311122");
        System.err.println(list);
    }

    @Test
    public void test11() {
        FunctionWithGroup functionWithGroup = functionWithGroupDao.findByFunctionIdAndGroupId(1, 2);
        System.err.println(functionWithGroup);
    }
}
