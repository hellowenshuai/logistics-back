package com.ansel;

import com.ansel.bean.*;
import com.ansel.dao.*;
import com.ansel.service.IRouteService;
import com.ansel.test.Blog;
import com.ansel.test.BlogRepository;
import com.ansel.util.Enctype;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author chenshuai
 */
@RunWith(SpringRunner.class)
@Slf4j
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

    @Autowired
    private BlogRepository blogRepository;


    @Autowired
    private StringRedisTemplate redisTemplate;// 解决Redis键值乱码问题

    @Resource
    private HashOperations<String, String, Blog> hashOperations;

    @Resource
    private ListOperations<String, String> listOperations;

    @Resource
    private SetOperations<String, String> setOperations;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Blog> zsetOperations;

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

//        routeInfoDao.truncateTable();
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

    @Test
    public void test12() {
        Blog blog = new Blog();
        blog.setTitle("xxxx新闻标题！");
        blog.setAuthorName("Chenss");
        blog.setContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        blog.setDelFlag(0);
        blogRepository.save(blog);
//        Spring Boot的自动配置会根据你的URL来推测驱动的，所以你提供JDBC URL、用户名和密码就好了。
//        JNDI不是连接池，你可以再去了解下JNDI是什么，你可以通过JNDI获取到事先配置好的连接池。
    }

    @Test
    public void testStringRedis() {
        try {
            Blog blog = new Blog();
            blog.setId(1l);
            blog.setTitle("news title");
            blog.setAuthorName("Chenss");
            blog.setContent("Hello,this is my frist use redis,very easy!");
            blog.setDelFlag(0);
            Boolean hasBlog = redisTemplate.hasKey("blogString");
            if (hasBlog) {
                String stringBlog = redisTemplate.opsForValue().get("blogString");
                log.info("blog1:" + stringBlog);
                DataType blogType = redisTemplate.type("blogString");
                log.info("blogType:" + blogType);
            } else {
                redisTemplate.opsForValue().set("blogString", blog.toString());
                log.info("add redis blog:" + blog);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    @Test
    public void testHashRedis() {
        Blog blog = new Blog();
        blog.setTitle("news title");
        blog.setAuthorName("Chenss");
        blog.setContent("Hello,this is my frist use redis,very easy!");
        blog.setDelFlag(0);

        Boolean blogHash = redisTemplate.hasKey("blogHash");

        if (blogHash) {
            List<Blog> blogHash1 = hashOperations.values("blogHash");
            log.error("hashBlog:" + blogHash1);
        } else {
            hashOperations.put("blogHash", "002", blog);
            log.error("add blogHash:" + blog);
        }
    }

    @Test
    public void testListRedis() {
        List<Blog> blogList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Blog blog = new Blog();
            long id = i + 1;
            blog.setId(id);
            blog.setTitle("中文标题");
            blog.setAuthorName("陈帅帅");
            blog.setContent("这是测试list类型使用的！");
            blog.setDelFlag(0);
            blogList.add(blog);
            listOperations.leftPush("blogList", blogList.get(i).toString());
        }


        Boolean flagList = listOperations.getOperations().hasKey("blogList");


//        if (flagList) {
//            String s = listOperations.leftPop("blogList");
//            Blog blog = JSONObject.parseObject(s, Blog.class);
//            log.error("blog:" + blog);
//        } else {
//            listOperations.leftPush("blogList", blogList.get(0).toString());
//            log.error("add blogList:" + blogList.get(0));
//        }
    }

    @Test
    public void testSetRedis() {
        List<Blog> blogSet = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Blog blog = new Blog();
            long id = i + 1;
            blog.setId(id);
            blog.setTitle("中文标题");
            blog.setAuthorName("陈帅帅");
            blog.setContent("这是测试list类型使用的！");
            blog.setDelFlag(0);
            blogSet.add(blog);
            setOperations.add("blogSet", blogSet.get(i).toString());
        }


        Boolean flagSet = setOperations.getOperations().hasKey("blogSet");


        if (flagSet) {
            List<String> blogSet1 = setOperations.pop("blogSet", 1);
            for (int i = 0; i < blogSet1.size(); i++) {
                String string = blogSet1.get(i);
                log.info("blogSet:" + string);
            }
        } else {
            for (int i = 0; i < blogSet.size(); i++) {
                setOperations.add("blogSet", blogSet.get(i).toString());
                log.info("blogSet" + i + ":" + blogSet.get(i).toString());
            }
        }
    }

    @Test
    public void testZSetRedis() {
        List<Blog> blogZSet = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Blog blog = new Blog();
            long id = i + 1;
            blog.setId(id);
            blog.setTitle("中文标题");
            blog.setAuthorName("陈帅帅");
            blog.setContent("这是测试list类型使用的！");
            blog.setDelFlag(0);
            blogZSet.add(blog);
            zsetOperations.add("blogZSet", blogZSet.get(i),80+i);
        }


        Boolean flagSet = zsetOperations.getOperations().hasKey("blogZSet");


        if (flagSet) {
            Long blogSet = zsetOperations.remove("blogZSet", Blog.class);
            for (int i = 0; i < blogZSet.size(); i++) {
                log.info("blogSet:" + blogSet);
            }
        } else {
            for (int i = 0; i < blogZSet.size(); i++) {
                zsetOperations.add("blogSet", blogZSet.get(i),80+i);
                log.info("blogSet" + i + ":" + blogZSet.get(i).toString());
            }
        }
    }
}
