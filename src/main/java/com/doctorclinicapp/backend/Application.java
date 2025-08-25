package com.doctorclinicapp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.doctorclinicapp.backend.model")
@EnableJpaRepositories(basePackages = "com.doctorclinicapp.backend.repository")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
