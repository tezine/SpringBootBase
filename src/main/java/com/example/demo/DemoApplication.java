package com.example.demo;

import com.example.demo.config.YAMLConfig;
import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

    @Autowired
    private YAMLConfig myConfig;
    @Autowired
    private EmployeeService service;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            System.out.println("using environment: " + myConfig.getEnvironment());
            System.out.println("name: " + myConfig.getName());
            System.out.println("servers: " + myConfig.getServers());        
            service.add(new Employee(1,"José"));
            service.add(new Employee(2,"Maria"));        
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
