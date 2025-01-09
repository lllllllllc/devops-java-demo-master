package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevopsJavaDemoApplication {

    public static void main(String[] args) {
//        args = new String[]{"--server.port=82", "--server.servlet.contextPath=/ssm"};
        SpringApplication.run(DevopsJavaDemoApplication.class, args);
    }

}
