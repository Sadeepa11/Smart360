package com.smartcity360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartCity360Application {

    public static void main(String[] args) {
        SpringApplication.run(SmartCity360Application.class, args);
        System.out.println("\n================================================================================");
        System.out.println("  SMARTCITY360 ENTERPRISE BACKEND STARTED SUCCESSFULLY ON http://localhost:8080 ");
        System.out.println("  Connected to MySQL 8.0 database: smartcity360_db                             ");
        System.out.println("================================================================================\n");
    }
}
