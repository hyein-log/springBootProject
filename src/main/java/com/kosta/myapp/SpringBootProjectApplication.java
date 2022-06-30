package com.kosta.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan("com.kosta") //Entity를 스캔해줌
//@ComponentScan("com.kosta") @Component, @Service, @Controller, @Repository(x)...springboot에서는 interface로 만들기 떄문에 불필요함
//@EnableJpaRepositories("com.kosta.myapp.repository") //기본패키지가 아닌곳의 repository를 스캔해야하는 경우 필요함
public class SpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}

}
