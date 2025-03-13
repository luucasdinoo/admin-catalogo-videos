package com.dino.admin.catalogo.infrastructure;

import com.dino.admin.catalogo.infrastructure.config.WebServerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dino.admin.catalogo")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }

    @RabbitListener(queues = "video.encoded.queue")
    void dummyListener(){}
}