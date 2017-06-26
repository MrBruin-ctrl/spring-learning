package springTest;

import cn.wang.springboot.App;
import cn.wang.springboot.domain.User;
import cn.wang.springboot.domain.UserRepository;
import cn.wang.springboot.schedule.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2017/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class Test1 {
    @Autowired
    private Task task;
    @Autowired
    private UserRepository userRepository;


    @Resource
    private RedisTemplate<String,User> redisTemplate;

    @Before
    public void init(){
        userRepository.save(new User("王诗涵",10));
    }

    //异步任务以及调度测试
    @org.junit.Test
    public void test() throws Exception {
        System.out.println("当前线程--"+Thread.currentThread());
        long start = System.currentTimeMillis();
        Future<String> task1 = task.doTaskOne();
        Future<String> task2 = task.doTaskTwo();
        Future<String> task3 = task.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

    //普通缓存测试
    @org.junit.Test
    public void test2(){
        User u1 = userRepository.findByName("王诗涵");
        System.out.println("第一次查询--"+u1);
        User u2 = userRepository.findByName("王诗涵");
        System.out.println("的二次查询--"+u2);
    }

    //redis集中缓存测试
    @org.junit.Test
    public void test3(){
        //cacheManager.getCacheNames().forEach(System.out::println);
        User u1 = userRepository.findByName("王诗涵");
        System.out.println("第一次查询--"+u1);
        User u2 = userRepository.findByName("王诗涵");
        System.out.println("的二次查询--"+u2);
        u2.setAge(30);
        userRepository.save(u2);
        User u4 = userRepository.findByName("王诗涵");
        System.out.println("第三次查询"+u4);

    }
    //Redis数据库测试
    @org.junit.Test
    public void test4(){
        User user = new User("aa", 20);
        redisTemplate.opsForValue().set(user.getName(), user);
        user = new User("蝙蝠侠", 30);
        redisTemplate.opsForValue().set(user.getName(), user);
        user = new User("蜘蛛侠", 40);
        redisTemplate.opsForValue().set(user.getName(), user);
        System.out.println(redisTemplate.opsForValue().get("蝙蝠侠"));
        Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().intValue());
        Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().intValue());
        Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().intValue());


    }









    //以下都为lambda表达式测试
    @org.junit.Test
    public void test12(){
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        Arrays.sort(atp,(String a1,String a2)-> a1.compareTo(a2));
        Arrays.asList(atp).forEach(System.out::println);
    }
    @org.junit.Test
    public void test13(){
        List<Integer> prediList = new ArrayList<Integer>();
        prediList.add(21);
        prediList.add(1);
        prediList.add(22);

        Predicate<Integer> stat1 = (num) -> num > 20;
        Predicate<Integer> stat2 = (num) -> num < 30;
        prediList.stream().filter(stat1.and(stat2)).forEach((num) -> System.out.println(num));
    }

}
