package com.dino.admin.catalogo.infrastructure;

import com.dino.admin.catalogo.infrastructure.config.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dino.admin.catalogo")
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(WebServerConfig.class, args);
    }
}