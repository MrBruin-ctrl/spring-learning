package cn.wang.springboot.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/10.
 */
@Component

public class ScheduleTaxk {
    @Scheduled(fixedRate = 5000)
    public void reportNow(){
        System.out.println("现在时间为："+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
}
