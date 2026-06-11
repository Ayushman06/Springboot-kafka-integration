package com.practice.springbootKafkaConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.practice.springbootKafkaConsumer")
public class SpringbootKafkaConsumerApplication {

    public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaConsumerApplication.class, args);
    }
}
