package cn.wang.springboot;

import cn.wang.springboot.domain.RedisObjectSerializer;
import cn.wang.springboot.domain.User;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableAsync
@EnableWebSecurity
@EnableCaching
public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        logger.debug("开始了");
        logger.error("测试");
        logger.info("试试");
        SpringApplication.run(App.class,args);
    }
}
