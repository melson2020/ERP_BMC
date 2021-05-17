package com.melson.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.melson"})
@Configuration
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.melson.base.dao",
        "com.melson.webserver.contract.dao",
        "com.melson.webserver.dict.dao",
        "com.melson.webserver.inventory.dao",
        "com.melson.webserver.order.dao",
        "com.melson.webserver.produce.dao"})//jpa repositry 路径
@EntityScan(basePackages = {"com.melson.base.entity",
        "com.melson.webserver.contract.entity",
        "com.melson.webserver.dict.entity",
        "com.melson.webserver.inventory.entity",
        "com.melson.webserver.order.entity",
        "com.melson.webserver.produce.entity",
        "com.melson.wechatmini.entity"})
// 3. Entity 所在的包
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }

}
