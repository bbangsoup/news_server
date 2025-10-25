package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// JSP + (tomcat(WAS) + Servlet)         web application server
// Sprin Model(Data) - View(template( Thymeleaf, mustache, Jinja ... ) - Control
// 통합된 구조 ---> 소규모 프로젝트, 간단한 웹 어플리케이션 제작시에 사용, 1인개발
// python : django, flask, fastapi

// Back-End
// Full-stack : Back-End + Front-End(react, android mobile)


@SpringBootApplication
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

}
